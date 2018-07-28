package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.events.OnRegistrationRequestEvent;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.UserRepo;
import com.cloud99.invest.repo.VerificationTokenRepo;
import com.cloud99.invest.repo.redis.AuthTokenRepo;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@CacheConfig(cacheNames = "user")
@Service
public class UserService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Value("${registration.request.token.exipire.in.hours}")
	public int registrationRequestTokenExpireInHours;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountService acctService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private VerificationTokenRepo tokenRepo;

	@Autowired
	private AuthTokenRepo authTokenRepo;

	public User getCurrentSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}

	@Cacheable(key = "#id")
	public AuthToken findAuthTokenByIdAndValidate(String id) {

		Optional<AuthToken> tokenOptional = findAuthTokenById(id);
		if (!tokenOptional.isPresent()) {
			throw new AccessDeniedException("auth.session.required");
		}
		return tokenOptional.get();
	}

	public Optional<AuthToken> findAuthTokenById(String id) {
		return authTokenRepo.findById(id);
	}

	public User findAndValidateUserByAuthToken(String authToken) {

		if (StringUtils.isBlank(authToken)) {
			throw new ServiceException("auth.token.required", null);
		}

		AuthToken token = findAuthTokenByIdAndValidate(authToken);

		if (token.getExpiryDateTime().isBeforeNow()) {
			authTokenRepo.delete(token);
			throw new AccessDeniedException("auth.token.expired");
		}

		// Optional<User> user = userRepo.findById(tokenOptional.get().getUserId());
		User user = findUserByIdAndValidate(token.getUserId());

		if (!user.isEnabled()) {
			throw new ServiceException("user.not.enabled", null);
		}
		return user;
	}


	@Cacheable(key = "'authToken-' + #authToken", unless = "#result != null")
	public AuthToken loginUser(String userEmail, String password) {

		User user = findUserByEmailAndValidate(userEmail);
		if (!user.isEnabled()) {
			throw new ServiceException("user.not.enabled", null);
		}
		if (!validatePassword(user.getPassword(), password)) {
			throw new ServiceException("invalid.user.or.password", null);
		}
		return createAuthToken(user.getId());
	}

	public AuthToken createAuthToken(String userEmail) {
		AuthToken token = new AuthToken(userEmail);
		authTokenRepo.save(token);
		return token;
	}

	private boolean validatePassword(String existingPassword, String incomingPassword) {
		return BCrypt.checkpw(incomingPassword, existingPassword);
	}

	@Transactional
	public User registerUserAndAccount(User user, String accountName, String callbackUrl) {
		LOGGER.debug("Starting to create new user and account: " + user);

		if (emailExist(user.getEmail())) {
			throw new ServiceException("user.email.exists", null, user.getEmail());
		}

		String pwHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(pwHash);
		user.setEnabled(false);

		user.setCreateDate(DateTime.now());
		user.addUserRole(UserRole.FREE_USER);

		user = userRepo.save(user);
		
		// TODO - NG - need to integrate credit card payment and provide inputs to
		// process card
		Account acct = acctService.createAccount(user, accountName, UserRole.FREE_USER);

		eventPublisher.publishEvent(new OnRegistrationRequestEvent(user.getEmail(), callbackUrl, acct));

		return user;
	}

	@Transactional
	public User confirmUserRegistration(String token) {
		VerificationToken tokenObj = tokenRepo.findByToken(token);

		if (tokenObj == null) {
			throw new EntityNotFoundException("Verification token", token);
		}

		User user = findUserByEmailAndValidate(tokenObj.getUserEmail());

		// has the token expired?
		if (tokenObj.getExpiryDate().isBeforeNow()) {
			handlExpiredVerificationToken(tokenObj);
			throw new ServiceException("registration.token.expire", null);
		}
		// token is good, activate account and user and delete token
		Account account = acctService.getAccount(tokenObj.getAccountId());
		account = acctService.activateAccount(account);
		user = activateUser(user);
		tokenRepo.delete(tokenObj);

		// TODO - NG - send Welcome email about account activation
		return user;
	}

	private void handlExpiredVerificationToken(VerificationToken token) {

		// delete token, user and account
		tokenRepo.delete(token);
		acctService.deleteAccount(token.getAccountId());
		deleteUser(token.getUserEmail());
	}

	@CachePut(key = "#user.getUserId", unless = "#result != null")
	private User activateUser(User user) {

		user = findUserByEmail(user.getEmail());
		user.setEnabled(true);
		return userRepo.save(user);
	}

	public VerificationToken createVerificationToken(String userEmail, String accountId) {
		LOGGER.debug("createVerificationToken() invoked for userEmail: " + userEmail);
		
		User user = findUserByEmailAndValidate(userEmail);

		VerificationToken token = tokenRepo.save(new VerificationToken(registrationRequestTokenExpireInHours, user.getId(), accountId));
		LOGGER.debug("Created new verification token: " + token);

		return token;
	}

	@CacheEvict(key = "#email")
	public void deleteUser(String email) {

		User user = userRepo.findByEmail(email);
		userRepo.delete(user);

	}

	@CachePut(key = "#result.getUserId", condition = "#result != null")
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = findUserByEmailAndValidate(username);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
	}

	public boolean emailExist(String email) {
		User user = findUserByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	public User findUserByIdAndValidate(String id) {

		Optional<User> optional = findUserById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new EntityNotFoundException("User", id);
		}
	}

	public Optional<User> findUserById(String id) {
		return userRepo.findById(id);
	}

	@Cacheable(key = "#email")
	public User findUserByEmailAndValidate(String email) {
		User user = findUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User", email);
		}
		return user;
	}

	public User findUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user;
	}

	@CacheEvict(key = "'authToken-' + #token")
	public void logoutUser(String token) {
		Optional<AuthToken> authTokenOptional = authTokenRepo.findById(token);

		if (authTokenOptional.isPresent()) {
			authTokenRepo.delete(authTokenOptional.get());
		}
	}

	public User addPropertyRefToUser(User user, String id) {

		user.getPropertyRefs().add(id);
		return userRepo.save(user);

	}
}
