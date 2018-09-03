package com.cloud99.invest.events;

import com.cloud99.invest.domain.account.Account;

import org.springframework.context.ApplicationEvent;

/**
 * Event that is fired when a new account is created
 *
 */
public class AccountCreatedEvent extends ApplicationEvent {
	private static final long serialVersionUID = 3879003516614094561L;

	public AccountCreatedEvent(Account account) {
		super(account);
	}

	public Account getAccount() {
		return (Account) this.getSource();
	}

}
