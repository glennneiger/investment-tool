package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;

public interface EmailServiceInterface {

	void sentUserRegistrationConfirmationEmail(User user, String appUrl);

}