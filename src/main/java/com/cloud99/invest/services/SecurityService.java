package com.cloud99.invest.services;

import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.VerificationTokenRepo;
import com.cloud99.invest.repo.redis.AuthTokenRepo;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityService implements UserDetailsService {

	@Value("${auth.token.time.to.live.in.seconds}")
	public int authTokenTimeToLiveInSeconds;

	@Value("${registration.request.token.exipire.in.hours}")
	public int registrationRequestTokenExpireInHours;

	@Value("${verification.token.time.to.live.in.seconds}")
	public int verificationTokenTimeToLiveInSeconds;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthTokenRepo authTokenRepo;

	@Autowired
	private AccountService acctService;

	@Autowired
	private VerificationTokenRepo tokenRepo;

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

		if (!Status.ACTIVE.equals(acct.getStatus())) {
			String msg = "Account is not active for user: " + email + " acct#: " + acct.getId();
			throw new ServiceException("account.not.active", msg);
		}
		log.debug("Logged in user {}", user.getId());
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
	@CacheEvict(key = "#result.email", condition = "#result == null")
	public User logoutUser(String token) {

		Optional<AuthToken> authTokenOptional = authTokenRepo.findById(token);

		if (authTokenOptional.isPresent()) {
			authTokenRepo.delete(authTokenOptional.get());
		}

		Optional<User> userOpt = userService.findUserById(authTokenOptional.get().getUserId());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setAuthenticated(false);
			return user;
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userService.findUserByEmailAndValidate(email);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
	}

	public User getCurrentSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}

	public void updateUserForSuccessfulAuthentication(String tokenId) {

		AuthToken token = findAuthTokenByIdAndValidate(tokenId);
		User user = userService.findUserByIdAndValidate(token.getUserId());
		user.setLastLoginDate(DateTime.now());

		token.setTimeToLiveSeconds(authTokenTimeToLiveInSeconds);
		authTokenRepo.save(token);
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

		if (token.getExpireDateTime().isBeforeNow()) {
			authTokenRepo.delete(token);
			throw new AccessDeniedException("auth.token.expired");
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

}
