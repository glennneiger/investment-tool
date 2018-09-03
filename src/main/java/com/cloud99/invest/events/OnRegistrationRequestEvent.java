package com.cloud99.invest.events;

import com.cloud99.invest.domain.account.Account;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

public class OnRegistrationRequestEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8406927110534724357L;

	@Getter
	@Setter
	private String appUrl;

	@Getter
	@Setter
	private String userEmail;

	@Getter
	@Setter
	private Account account;

	public OnRegistrationRequestEvent(String userEmail, String appUrl, Account account) {
		super(userEmail);

		this.userEmail = userEmail;
		this.appUrl = appUrl;
		this.account = account;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
