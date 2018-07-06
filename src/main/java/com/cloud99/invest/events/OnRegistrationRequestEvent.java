package com.cloud99.invest.events;

import com.cloud99.invest.domain.account.Account;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationRequestEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8406927110534724357L;
	private String appUrl;
	private String userEmail;
	private Account account;

	public OnRegistrationRequestEvent(String userEmail, String appUrl, Account account) {
		super(userEmail);

		this.userEmail = userEmail;
		this.appUrl = appUrl;
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
