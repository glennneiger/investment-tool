package com.cloud99.invest.services;

import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.GeneralSettings;
import com.cloud99.invest.domain.account.SubscriptionType;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.events.AccountCreatedEvent;
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

	@Value("${free.user.num.of.properties}")
	private Integer freeUserNumOfProperties;


	public Account createAccount(AccountCreationRequest request, User owner) {

		Account account = new Account();
		account.setStatus(Status.PENDING);
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
		account.setAccountOptions(acctOptions);
		
		log.debug("Created new account: " + account);
		
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
