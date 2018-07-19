package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.events.OnRegistrationRequestEvent;
import com.cloud99.invest.repo.UserRepo;
import com.cloud99.invest.repo.VerificationTokenRepo;
import com.cloud99.invest.repo.redis.AuthTokenRepo;
import com.cloud99.invest.services.exceptions.EntityNotFoundException;
import com.cloud99.invest.services.exceptions.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

	public User findAndValidateUserByAuthToken(String authToken) {

		if (StringUtils.isBlank(authToken)) {
			throw new EntityNotFoundException("auth.token.required");
		}

		Optional<AuthToken> tokenOptional = authTokenRepo.findById(authToken);
		if (!tokenOptional.isPresent()) {
			throw new AccessDeniedException("auth.session.required");
		}

		if (tokenOptional.get().getExpiryDateTime().isBeforeNow()) {
			authTokenRepo.delete(tokenOptional.get());
			throw new AccessDeniedException("auth.token.expired");
		}

		Optional<User> user = userRepo.findById(tokenOptional.get().getUserId());

		if (!user.isPresent() || !user.get().isEnabled()) {
			throw new ServiceException("user.not.enabled");
		}
		return user.get();
	}

	public AuthToken loginUser(String userEmail, String password) {

		User user = findUserByEmailAndValidate(userEmail);
		if (!user.isEnabled()) {
			throw new ServiceException("user.not.enabled");
		}
		if (!validatePassword(user.getPassword(), password)) {
			throw new ServiceException("invalid.user.or.password");
		}
		return createAuthToken(user.getObjectId().toString());
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
			throw new ServiceException("user.email.exists");
		}

		String pwHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(pwHash);
		user.setEnabled(false);

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
			throw new EntityNotFoundException("account.not.found");
		}

		User user = findUserByEmail(tokenObj.getUserEmail());
		if (user == null) {
			throw new EntityNotFoundException("user.not.found");
		}
		// has the token expired?
		if (tokenObj.getExpiryDate().isBeforeNow()) {
			handlExpiredVerificationToken(tokenObj);
			throw new ServiceException("registration.token.expire");
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

	private User activateUser(User user) {

		user = findUserByEmail(user.getEmail());
		user.setEnabled(true);
		return userRepo.save(user);
	}

	public VerificationToken createVerificationToken(String userEmail, String accountId) {
		LOGGER.debug("createVerificationToken() invoked for userEmail: " + userEmail);
		
		User user = findUserByEmail(userEmail);
		if (user == null) {
			throw new ServiceException("user.not.found");
		}

		VerificationToken token = tokenRepo.save(new VerificationToken(registrationRequestTokenExpireInHours, user.getObjectId().toString(), accountId));
		LOGGER.debug("Created new verification token: " + token);

		return token;
	}

	public void deleteUser(String email) {

		User user = userRepo.findByEmail(email);
		userRepo.delete(user);

	}

	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = findUserByEmailAndValidate(username);
		return user;
	}

	public boolean emailExist(String email) {
		User user = findUserByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	public Optional<User> findUserById(String id) {
		return userRepo.findById(id);
	}

	public User findUserByEmailAndValidate(String email) {
		User user = findUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("user.not.found");
		}
		return user;
	}

	public User findUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user;
	}

//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		authentication.getPrincipal();
//		authentication.setAuthenticated(true);
//		return authentication;
//	}
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		if (User.class.isAssignableFrom(authentication)) {
//			return true;
//		}
//		return false;
//	}

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
