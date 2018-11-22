package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.AccountSettings;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.PropertyRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PropertyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepo propertyRepo;
	
	@Autowired
	private AccountService accountService;

	public <T extends Property> T createProperty(String userEmail, T property) {
		LOGGER.debug("Starting to create a new property: " + property.toJsonString());

		// validate user
		User user = userService.findUserByEmailAndValidate(userEmail);
		
		// does the user have the ability to add more documents?
		// get count of users documents
		if (!canUserAddProperty(user)) {
			log.warn("User cannot add any more properties");
			throw new ServiceException("user.too.many.properties");
		}
		
		Property dbProperty = propertyRepo.save(property);

		if (user.getPropertyRefs() == null) {
			user.setPropertyRefs(new ArrayList<>());
		}

		userService.addPropertyRefToUser(user, dbProperty.getId());

		return property;
	}

	private boolean canUserAddProperty(User user) {
		AccountSettings acctSettings = accountService.getAccountsGeneralSettingForCurrentUser();
		if (acctSettings.getNumberOfPropertiesUserCanStore() >= user.getPropertyRefs().size()) {
			return false;
		}
		return true;

	}

	public Iterable<Property> getAllUsersPropertyDetails(String userEmail, String accountId) {
		LOGGER.trace("getProperty() : " + userEmail + " for account: " + accountId);

		User user = userService.findUserByEmailAndValidate(userEmail);
		Iterable<Property> props = propertyRepo.findAllById(convertIteratorToIterable(user.getPropertyRefs().iterator()));
		return props;

	}

	public void deleteProperty(Property property) {

		propertyRepo.delete(property);
	}

	public Property updateProperty(String userEmail, @Validated Property property) {
		userService.findUserByEmailAndValidate(userEmail);
		return propertyRepo.save(property);
	}

	public void deleteProperty(String userEmail, String propertyId) {
		User user = userService.findUserByEmailAndValidate(userEmail);

		if (user.getPropertyRefs().contains(propertyId)) {
			propertyRepo.deleteById(propertyId);
		} else {
			throw new ServiceException("user.not.authorized.property", null, propertyId);
		}
	}

	public Property getAndValidateProperty(String propertyId) {

		Optional<Property> optional = propertyRepo.findById(propertyId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("Property", propertyId);
		}
		return optional.get();
	}

	/**
	 * Quick easy Java 8 way to convert an Iterator into an Iterable
	 */
	private <T> Iterable<T> convertIteratorToIterable(Iterator<T> iter) {
		return () -> iter;
	}
}
