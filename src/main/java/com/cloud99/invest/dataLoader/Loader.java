package com.cloud99.invest.dataLoader;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.Name;
import com.cloud99.invest.domain.Person.Gender;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Loader {

	public static ObjectMapper objMapper = new ObjectMapper();

	@Autowired
	private UserService userService;

	public static void main(String[] args) throws Exception {
		User user = buildUser();
		Property p = buildProperty();
		System.out.println(objMapper.writeValueAsString(p));
	}

	private static User buildUser() {
		User user = new User();
		user.setUserRoles(Arrays.asList(UserRole.FREE_USER));
		user.setEmail("nickgilas@gmail.com");
		user.setPassword("password");
		user.setMatchingPassword("password");
		user.setName(buildName("Nick", "Gilas"));
		user.setBirthDate(org.joda.time.LocalDate.now().withYear(1980).withMonthOfYear(3).withDayOfMonth(23));
		user.setGender(Gender.MALE);
		return user;
	}

	private static Property buildProperty() {
		SingleFamilyProperty p = new SingleFamilyProperty();
		p.setAddress(buildAddress());
		p.setName("My single family home");
		p.setBathrooms(2.5f);
		p.setBedrooms(3);
		p.setFinishedSqFt(2700);
		return p;
	}

	private static Address buildAddress() {

		Address a = new Address();
		a.setAddress1("13060 W. 64th Place");
		a.setCity("Arvada");
		a.setState("Colorado");
		a.setZip("80004");
		return a;
	}

	private static Name buildName(String first, String last) {
		Name name = new Name();
		name.setFirstName(first);
		name.setLastName(last);
		return name;
	}

}
