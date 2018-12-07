package com.cloud99.invest.services;

import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.MembershipType;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.domain.billing.SubscriptionMembership;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = { "users" })
@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountService acctService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SubscriptionService subscriptionService;

	public Account registerUserAndAccount(AccountCreationRequest accountRequest, String callbackUrl) {

		LOGGER.debug("Starting to create new user and account: {}, callback url: {}", accountRequest, callbackUrl);

		User newUser = copyRequestUserAttributes(accountRequest);

		newUser = createFreeUser(newUser, UserRole.CUSTOMER);

		Account acct = null;
		try {
			// does this user already have an account?
			acct = acctService.createAccount(accountRequest, newUser);
		} catch (AccountAlreadyExistsException e) {
			if (newUser != null && acct != null) {
				Optional<User> acctUserOpt = this.findUserById(acct.getOwnerId());
				if (acctUserOpt.isPresent()) {
					newUser = acctUserOpt.get();
				} else {
					deleteUser(newUser.getId());
					throw e;
				}
			}
		}

		// NG - this is slated for a future release
		// eventPublisher.publishEvent(new
		// OnRegistrationRequestEvent(newUser.getEmail(), callbackUrl, acct));

		return acct;
	}

	@Cacheable(key = "#result.id", condition = "#result != null")
	public User createFreeUser(User user, UserRole userRole) {
		if (emailExist(user.getEmail())) {
			throw new ServiceException("user.email.exists", null, user.getEmail());
		}

		DateTime now = DateTime.now();
		String pwHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(pwHash);
		user.setEnabled(true);

		user.setCreateDate(now);

		// make sure there are no roles inadvertently added
		user.setUserRoles(null);
		List<UserRole> roles = new ArrayList<>();
		roles.add(userRole);
		user.setUserRoles(roles);
		user.setMembershipType(MembershipType.FREE);

		Subscription freeSubscription = subscriptionService.getFreeSubscription();
		user.setSubscriptionMembership(buildSubscriptionMembership(MembershipType.FREE, freeSubscription, now, true));
		return userRepo.save(user);
	}

	private SubscriptionMembership buildSubscriptionMembership(MembershipType membershipType, Subscription sub, DateTime createDate, boolean active) {
		SubscriptionMembership sm = new SubscriptionMembership();
		sm.setSubscriptionId(sub.getId());
		sm.setCreateDate(createDate);
		sm.setActive(active);
		sm.setMembershipType(membershipType);

		return sm;
	}

	public User activateUser(String id) {

		User user = findUserByIdAndValidate(id);
		user.setEnabled(true);
		return userRepo.save(user);
	}

	@CacheEvict(key = "#result.id")
	public User deleteUser(String id) {
		Optional<User> userOpt = findUserById(id);
		if (userOpt.isPresent()) {
			userRepo.deleteById(id);
			return userOpt.get();
		}
		return null;

	}

	@Cacheable(key = "#result.id", condition = "#result != null")
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Cacheable(key = "'id.' + #result.id", condition = "#result != null")
	public User findUserByIdAndValidate(String id) {

		Optional<User> optional = findUserById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new EntityNotFoundException("User", id);

	}

	@Cacheable(key = "#id", condition = "#result != null")
	public Optional<User> findUserById(String id) {
		return userRepo.findById(id);
	}

	@Cacheable(key = "#result.id", condition = "#result != null")
	public User findUserByEmailAndValidate(String email) {
		User user = findUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User", email);
		}
		return user;
	}

	@CacheEvict(key = "#result.id", condition = "#result != null")
	public User clearUserCache(String id) {
		// just need to return the user so the cache gets evicted for this user
		Optional<User> userOpt = userRepo.findById(id);
		return userOpt.get();
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
		user.setGender(accountRequest.getGender());
		user.setPassword((accountRequest.getPassword()));

		return user;
	}
}
