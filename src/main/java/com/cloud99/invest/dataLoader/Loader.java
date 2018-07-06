package com.cloud99.invest.dataLoader;

import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Loader {

	public static ObjectMapper objMapper = new ObjectMapper();

	@Autowired
	private UserService userService;

	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setUserRoles(Arrays.asList(UserRole.FREE_USER));
		user.setEmail("nickgilas@gmail.com");
		user.setPassword("password");
		user.setMatchingPassword("password");
		user.setName(buildName("Nick", "Gilas"));
		user.setBirthDate(org.joda.time.LocalDate.now().withYear(1980).withMonthOfYear(3).withDayOfMonth(23));
		user.setGender(Gender.MALE);

		System.out.println(objMapper.writeValueAsString(user));
	}

	private static Name buildName(String first, String last) {
		Name name = new Name();
		name.setFirstName(first);
		name.setLastName(last);
		return name;
	}

}
