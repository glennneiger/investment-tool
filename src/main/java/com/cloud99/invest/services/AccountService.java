package com.cloud99.invest.services;

import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.repo.AccountRepo;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
public class AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountRepo acctRepo;

	@Value("${free.user.num.of.properties}")
	private Integer freeUserNumOfProperties;

	public Account createAccount(User owner, String accountName, UserRole role) {

		Account account = new Account();
		account.setStatus(Status.PENDING);
		account.setOwnerId(owner.getId());
		account.setCreateDate(DateTime.now());
		account.setName(accountName);

		if (UserRole.FREE_USER.equals(role)) {
			account.setNumberOfPropertiesAllowed(freeUserNumOfProperties);
		}

		account = acctRepo.save(account);
		LOGGER.debug("Created new account: " + account);

		return account;
	}

	public Account getOwnersAccountAndValidate(String userId) {
		Account acct = acctRepo.findByOwnerId(userId);
		if (acct == null) {
			throw new EntityNotFoundException("Account", "");
		}
		return acct;
	}

	public Long deleteAccountByName(String name) {
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
		}

	}

}
