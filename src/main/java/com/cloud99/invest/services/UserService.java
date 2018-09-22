package com.cloud99.invest.services;

import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.SubscriptionType;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.exceptions.AccountAlreadyExistsException;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.UserRepo;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@CacheConfig(cacheNames = { "users" })
@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountService acctService;

	public Account registerUserAndAccount(AccountCreationRequest accountRequest, String callbackUrl) {

		LOGGER.debug("Starting to create new user and account: {}, callback url: {}", accountRequest, callbackUrl);

		User newUser = copyRequestUserAttributes(accountRequest);

		// does this user already have an account?
		newUser = createUser(newUser, UserRole.CUSTOMER);

		newUser.setSubscriptionType(SubscriptionType.FREE);

		// TODO - NG - need to integrate credit card payment and provide inputs to
		// process card

		// TODO - NG - need to see if credit cards processing was successful and then
		// add the PAID subscriptionType

		Account acct = null;
		try {
			acct = acctService.createAccount(accountRequest, newUser);
		} catch (AccountAlreadyExistsException e) {
			if (newUser != null) {
				deleteUser(newUser.getId());
				throw e;
			}
		}

		// NG - this is slated for a future release
		// eventPublisher.publishEvent(new
		// OnRegistrationRequestEvent(newUser.getEmail(), callbackUrl, acct));

		return acct;
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

	public User activateUser(String userId) {

		User user = findUserByIdAndValidate(userId);
		user.setEnabled(true);
		return userRepo.save(user);
	}

	// @CacheEvict(key = "#result.email")
	public User deleteUser(String userId) {
		Optional<User> userOpt = findUserById(userId);
		if (userOpt.isPresent()) {
			userRepo.deleteById(userId);
			return userOpt.get();
		}
		return null;

	}

	// @Cacheable(key = "#result.email", unless = "#result == null")
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	// @Cacheable(key = "'userId.' + #result.id", unless = "#result == null")
	public User findUserByIdAndValidate(String id) {

		Optional<User> optional = findUserById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new EntityNotFoundException("User", id);

	}

	// TODO Cache this method
	public Optional<User> findUserById(String id) {
		return userRepo.findById(id);
	}

	@Cacheable(key = "'userId.' + #email", unless = "#result == null")
	public User findUserByEmailAndValidate(String email) {
		User user = findUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User", email);
		}
		return user;
	}


	public User addPropertyRefToUser(User user, String id) {

		user.getPropertyRefs().add(id);
		return userRepo.save(user);
	}

	private User findUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user;
	}

	private boolean emailExist(String email) {
		User user = findUserByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	private User copyRequestUserAttributes(AccountCreationRequest accountRequest) {
		User user = new User();
		user.setPersonName(new Name(accountRequest.getFirstName(), accountRequest.getMiddleName(), accountRequest.getLastName()));
		user.setBirthDate(accountRequest.getBirthDate());
		user.setEmail(accountRequest.getEmail());
		user.setLocale(accountRequest.getLocale());
		user.setGender(accountRequest.getGender());
		user.setPassword((accountRequest.getPassword()));
		user.setLocale(accountRequest.getLocale());

		return user;
	}
}
