package com.cloud99.invest.services;

import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.GeneralSettings;
import com.cloud99.invest.domain.account.SubscriptionType;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.events.AccountCreatedEvent;
import com.cloud99.invest.exceptions.AccountAlreadyExistsException;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.AccountRepo;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class AccountService {

	@Autowired
	private AccountRepo acctRepo;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private SecurityService securityService;

	@Value("${free.user.num.of.properties}")
	private Integer freeUserNumOfProperties;

	public Account updateAccount(Account account) {

		return acctRepo.save(account);
	}
	public GeneralSettings getAccountsGeneralSettingForCurrentUser() {
		User user = securityService.getCurrentSessionUser();
		Account acct = getOwnersAccountAndValidate(user.getId());

		return acct.getGeneralSettings();
	}

	public Account createAccount(AccountCreationRequest request, User owner) {

		// does an account exist for this user already?
		Account acct = acctRepo.findByOwnerId(owner.getId());
		if (acct != null) {
			throw new AccountAlreadyExistsException(owner.getId(), acct.getId());
		}
		Account account = new Account();
		account.setStatus(Status.ACTIVE);
		account.setOwnerId(owner.getId());
		account.setCreateDate(DateTime.now());
		account.setName(request.getAccountName());
		account = acctRepo.save(account);
		
		Integer numOfProperties = freeUserNumOfProperties;
		GeneralSettings acctOptions = new GeneralSettings();
		if (SubscriptionType.PAID.equals(request.getSubscription())) {
			numOfProperties = -1;
		}

		acctOptions.setStoredDocumentCount(numOfProperties);
		account.setGeneralSettings(acctOptions);
		
		log.debug("Created new account: " + account);
		
		activateAccount(account);

		eventPublisher.publishEvent(new AccountCreatedEvent(account));
		return account;
	}

	public Account getOwnersAccountAndValidate(String userId) {
		Account acct = acctRepo.findByOwnerId(userId);
		
		if (acct == null) {
			String msg = "Account not found for user: " + userId;
			log.warn(msg);
			throw new EntityNotFoundException("Account", msg);
		}
		if (!Status.ACTIVE.equals(acct.getStatus())) {
			String msg = "Account is not active for user: " + userId + " acct#: " + acct.getId();
			throw new ServiceException("account.not.active", msg);
		}

		if (!Status.ACTIVE.equals(acct.getStatus())) {
			log.error("User account is not active, need to see why this is as all accounts should be active, email: {}, account: {}", userId, acct.getId());
			throw new ServiceException("user.not.enabled");
		}
		return acct;
	}

	public Long deleteAccountByName(String name) {
		log.debug("Going to delete account by name: " + name);
		return acctRepo.deleteByName(name);
	}

	public Account getAccountAndValidate(String accountId) {
		Optional<Account> optional = acctRepo.findById(accountId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new EntityNotFoundException("Account", accountId);
	}

	public Account activateAccount(Account account) {

		account.setStatus(Status.ACTIVE);
		return acctRepo.save(account);
	}

	public void deleteAccount(String accountId) {
		acctRepo.deleteById(accountId);

	}

	public void deleteOwnersAccount(String userId) {
		try {
			Account acct = getOwnersAccountAndValidate(userId);
			acctRepo.delete(acct);
		} catch (EntityNotFoundException e) {
			// nothing to worry about, account doesn't exist
			log.warn("No account assigned to user could be found: " + userId, e);
		}

	}

	public void save(Account acct) {
		acctRepo.save(acct);
	}

}
