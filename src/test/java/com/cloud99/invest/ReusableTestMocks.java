package com.cloud99.invest;

import com.cloud99.invest.services.UserService;

import org.mockito.Mock;
import org.mockito.Mockito;

public interface ReusableTestMocks {

	@Mock
	public UserService userService = Mockito.mock(UserService.class);
}
