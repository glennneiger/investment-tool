package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.SecurityException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.VerificationTokenRepo;
import com.cloud99.invest.repo.redis.AuthTokenRepo;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "securityService")
public class SecurityService implements UserDetailsService {

	@NotNull
	@Value("${auth.token.time.to.live.in.seconds}")
	public Integer authTokenTimeToLiveInSeconds;

	@NotNull
	@Value("${registration.request.token.exipire.in.hours}")
	public Integer registrationRequestTokenExpireInHours;

	@NotNull
	@Value("${verification.token.time.to.live.in.seconds}")
	public Integer verificationTokenTimeToLiveInSeconds;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthTokenRepo authTokenRepo;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	private AccountService acctService;

	@Autowired
	private VerificationTokenRepo tokenRepo;

	public boolean isAccountOwner() {
		String accountId = "Test";
		Account existingAcct = acctService.getAccountAndValidate(accountId);
		User user = getCurrentSessionUser();
		if (!existingAcct.getOwnerId().equals(user.getId())) {
			return false;
		}
		return true;
	}

	// NG - this feature is slated for a future release (not active now)
	/**
	 * This method should be invoked from an HTTP client/new user to confirm their
	 * email address and activate their account.
	 * 
	 * @param token
	 *            to be verified
	 * @return the active user object
	 */
	public User confirmUserRegistration(String token) {
		VerificationToken tokenObj = tokenRepo.findByToken(token);

		if (tokenObj == null) {
			throw new EntityNotFoundException("Verification token", token);
		}

		User user = userService.findUserByIdAndValidate(tokenObj.getUserId());

		// has the token expired?
		if (tokenObj.getExpireDateTime().isBeforeNow()) {
			handlExpiredVerificationToken(tokenObj);
			throw new ServiceException("registration.token.expire", null);
		}
		// token is good, activate account and user and delete token
		Account account = acctService.getOwnersAccountAndValidate(user.getId());
		account = acctService.activateAccount(account);
		user = userService.activateUser(user.getId());
		tokenRepo.delete(tokenObj);

		// TODO - NG - send Welcome email about account activation
		return user;
	}

	public AuthToken loginUser(String email, String password) {

		User user = userService.findUserByEmailAndValidate(email);
		if (!user.isEnabled()) {
			throw new ServiceException("user.not.enabled", null);
		}

		if (!validatePassword(user.getPassword(), password)) {
			throw new ServiceException("invalid.user.or.password", null);
		}

		Account acct = acctService.getOwnersAccountAndValidate(user.getId());

		log.debug("Logged in user: {}, account: {}", user.getId(), acct.getId());
		
		return createAuthToken(user.getId());
	}

	/**
	 * We must return the user from this method to ensure that the cache is cleared
	 * out.
	 * 
	 * @param token
	 *            of the user to log out
	 * @return the user who was logged out
	 */
	@CacheEvict(key = "#result.id", condition = "#result == null")
	public User logoutUser(String token) {

		// delete auth token from cache
		Optional<AuthToken> authTokenOptional = authTokenRepo.findById(token);

		if (authTokenOptional.isPresent()) {
			authTokenRepo.delete(authTokenOptional.get());
		}


		Optional<User> userOpt = userService.findUserById(authTokenOptional.get().getUserId());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setAuthenticated(false);
			// remove user from cache
			userService.clearUserCache(user.getId());
			return user;
		}
		// no user found, nothing to return
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userService.findUserByEmailAndValidate(email);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
	}

	public User getCurrentSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		return userService.findUserByEmailAndValidate(userDetails.getUsername());
	}

	public void updateUserForSuccessfulAuthentication(String tokenId) {

		AuthToken token = findAuthTokenByIdAndValidate(tokenId);
		User user = userService.findUserByIdAndValidate(token.getUserId());
		user.setLastActivityDate(DateTime.now());
		userService.updateUser(user);

		token.setExpireTime(DateTime.now().plusSeconds(authTokenTimeToLiveInSeconds));
		authTokenRepo.save(token);

		boolean didUpdateExipre = redisTemplate.expireAt("AuthToken:" + tokenId, token.getExpireTime());
		if (!didUpdateExipre) {
			log.error("Unable to update TTL for auth token: " + tokenId + ", there is something really fucked up happened and we are not able to update any TTL for the auth tokens");
		}
	}

	public AuthToken createAuthToken(String userId) {
		AuthToken token = new AuthToken(userId, authTokenTimeToLiveInSeconds);
		authTokenRepo.save(token);
		return token;
	}

	public User findAndValidateUserByAuthToken(String authToken) {

		if (StringUtils.isBlank(authToken)) {
			throw new ServiceException("auth.token.required", null);
		}

		AuthToken token = findAuthTokenByIdAndValidate(authToken);

		if (token.isExpired()) {
			authTokenRepo.delete(token);
			log.warn("Auth token expired, access denied for token: " + authToken);
			throw new CredentialsExpiredException("auth.token.expired");
		}

		User user = userService.findUserByIdAndValidate(token.getUserId());

		if (!user.isEnabled()) {
			throw new ServiceException("user.not.enabled", null);
		}

		return user;
	}

	public VerificationToken createVerificationToken(String userEmail) {
		log.debug("createVerificationToken() invoked for userEmail: " + userEmail);

		User user = userService.findUserByEmailAndValidate(userEmail);

		VerificationToken token = tokenRepo.save(new VerificationToken(user.getId(), verificationTokenTimeToLiveInSeconds));
		log.debug("Created new verification token: " + token);

		return token;
	}

	private void handlExpiredVerificationToken(VerificationToken token) {

		// delete token, user and account
		tokenRepo.delete(token);
		acctService.deleteOwnersAccount(token.getUserId());
		userService.deleteUser(token.getUserId());
	}

	private boolean validatePassword(String existingPassword, String incomingPassword) {
		return BCrypt.checkpw(incomingPassword, existingPassword);
	}

	private AuthToken findAuthTokenByIdAndValidate(String id) {

		Optional<AuthToken> tokenOptional = findAuthTokenById(id);
		if (!tokenOptional.isPresent()) {
			throw new ServiceException("auth.session.required", "no auth token found");
		}
		return tokenOptional.get();
	}
	private Optional<AuthToken> findAuthTokenById(String id) {
		return authTokenRepo.findById(id);
	}

	public AuthToken registerUserAndAccount(AccountCreationRequest accountRequest, String contextPath) {
		Account acct = userService.registerUserAndAccount(accountRequest, contextPath);
		User newUser = userService.findUserByIdAndValidate(acct.getOwnerId());
		return createAuthToken(newUser.getId());
	}

}
