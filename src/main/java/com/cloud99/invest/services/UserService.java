package com.cloud99.invest.services;

import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.events.OnRegistrationRequestEvent;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.UserRepo;
import com.cloud99.invest.repo.VerificationTokenRepo;
import com.cloud99.invest.repo.redis.AuthTokenRepo;
import com.sun.scenario.effect.Effect.AccelType;

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

	@Value("${auth.token.expire.time.in.minutes}")
	public int authTokenExpireTimeInMinutes;

	@Value("${verification.token.expire.time.in.minutes}")
	public int verificationTokenExpireTimeInMinutes;

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

	public void updateUserTokenExpireTime(String tokenId) {
		AuthToken token = findAuthTokenByIdAndValidate(tokenId);

		token.updateExpireDateFromNow(authTokenExpireTimeInMinutes);
		authTokenRepo.save(token);
	}

	public AuthToken findAuthTokenByIdAndValidate(String id) {

		Optional<AuthToken> tokenOptional = findAuthTokenById(id);
		if (!tokenOptional.isPresent()) {
			throw new ServiceException("auth.session.required", "no auth token found");
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

		if (token.getExpireDateTime().isBeforeNow()) {
			authTokenRepo.delete(token);
			throw new AccessDeniedException("auth.token.expired");
		}

		User user = findUserByIdAndValidate(token.getUserId());

		if (!user.isEnabled()) {
			throw new ServiceException("user.not.enabled", null);
		}
		return user;
	}

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

	public AuthToken createAuthToken(String userId) {
		AuthToken token = new AuthToken(userId, authTokenExpireTimeInMinutes);
		authTokenRepo.save(token);
		return token;
	}

	private boolean validatePassword(String existingPassword, String incomingPassword) {
		return BCrypt.checkpw(incomingPassword, existingPassword);
	}

	@Transactional
	public Account registerUserAndAccount(AccountCreationRequest accountRequest, String callbackUrl) {
		LOGGER.debug("Starting to create new user and account: {}, callback url: {}", accountRequest, callbackUrl);

		User newUser = copyRequestUserAttributes(accountRequest);
		newUser = createUser(newUser, UserRole.CUSTOMER);

		// TODO - NG - need to integrate credit card payment and provide inputs to
		// process card
		Account acct = acctService.createAccount(accountRequest, newUser);

		eventPublisher.publishEvent(new OnRegistrationRequestEvent(newUser.getEmail(), callbackUrl, acct));

		return acct;
	}

	private User copyRequestUserAttributes(AccountCreationRequest accountRequest) {
		User user = new User();
		user.setPersonName(new Name(accountRequest.getFirstName(), accountRequest.getMiddleName(), accountRequest.getLastName()));
		user.setBirthDate(accountRequest.getBirthDate());
		user.setEmail(accountRequest.getEmail());
		user.setLocale(accountRequest.getLocale());
		user.setGender(accountRequest.getGender());

		return user;
	}

	public User createUser(User user, UserRole userRole) {
		if (emailExist(user.getEmail())) {
			throw new ServiceException("user.email.exists", null, user.getEmail());
		}

		String pwHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(pwHash);
		user.setEnabled(false);

		user.setCreateDate(DateTime.now());

		// make sure there are no roles incorrectly added
		user.setUserRoles(null);
		user.addUserRole(userRole);

		return userRepo.save(user);
	}

	@Transactional
	public User confirmUserRegistration(String token) {
		VerificationToken tokenObj = tokenRepo.findByToken(token);

		if (tokenObj == null) {
			throw new EntityNotFoundException("Verification token", token);
		}

		User user = findUserByIdAndValidate(tokenObj.getUserId());

		// has the token expired?
		if (tokenObj.getExpireDateTime().isBeforeNow()) {
			handlExpiredVerificationToken(tokenObj);
			throw new ServiceException("registration.token.expire", null);
		}
		// token is good, activate account and user and delete token
		Account account = acctService.getOwnersAccountAndValidate(user.getId());
		account = acctService.activateAccount(account);
		user = activateUser(user);
		tokenRepo.delete(tokenObj);

		// TODO - NG - send Welcome email about account activation
		return user;
	}

	private void handlExpiredVerificationToken(VerificationToken token) {

		// delete token, user and account
		tokenRepo.delete(token);
		acctService.deleteOwnersAccount(token.getUserId());
		deleteUser(token.getUserId());
	}

	private User activateUser(User user) {

		user = findUserByIdAndValidate(user.getId());
		user.setEnabled(true);
		return userRepo.save(user);
	}

	public VerificationToken createVerificationToken(String userEmail) {
		LOGGER.debug("createVerificationToken() invoked for userEmail: " + userEmail);
		
		User user = findUserByEmailAndValidate(userEmail);

		VerificationToken token = tokenRepo.save(new VerificationToken(user.getId(), verificationTokenExpireTimeInMinutes));
		LOGGER.debug("Created new verification token: " + token);

		return token;
	}

	@CacheEvict(key = "#userId")
	public void deleteUser(String userId) {
		userRepo.deleteById(userId);

	}

	@CachePut(key = "#result.getUserId", condition = "#result != null")
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	// TODO - NG - cache this
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
		}
		throw new EntityNotFoundException("User", id);

	}

	public Optional<User> findUserById(String id) {
		return userRepo.findById(id);
	}

	public User findUserByEmailAndValidate(String email) {
		User user = findUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User", email);
		}
		return user;
	}

	@Cacheable(key = "#result.getUserId", unless = "#result != null")
	public User findUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user;
	}

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

	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
}
