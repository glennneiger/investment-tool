package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.events.OnRegistrationRequestEvent;
import com.cloud99.invest.repo.UserRepo;
import com.cloud99.invest.repo.VerificationTokenRepo;
import com.cloud99.invest.services.exceptions.EntityNotFoundException;
import com.cloud99.invest.services.exceptions.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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

	public String loginUser(String userEmail, String password) {
		User user = findUserByEmailAndValidate(userEmail);

		return null;
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

		VerificationToken token = tokenRepo.save(new VerificationToken(UUID.randomUUID().toString(), registrationRequestTokenExpireInHours, userEmail, accountId));
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
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), buildAuthorities(user.getUserRoles()));
	}

	public boolean emailExist(String email) {
		User user = findUserByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
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

	private Collection<? extends GrantedAuthority> buildAuthorities(Collection<UserRole> applicationRoles) {

		Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
		for (UserRole role : applicationRoles) {
			auths.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		return auths;
	}
}
