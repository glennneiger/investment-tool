package com.cloud99.invest.services;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.UserProperty;
import com.cloud99.invest.repo.PropertyRepo;
import com.cloud99.invest.repo.UserPropertyRepo;
import com.cloud99.invest.services.exceptions.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class PropertyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);

	@Autowired
	private UserPropertyRepo userPropertyRepo;

	@Autowired
	private PropertyRepo propertyRepo;
	
	
	public <T extends Property> Property createProperty(T property) {
		LOGGER.trace("Starting to create a new property: " + property.toJsonString());

		property = propertyRepo.save(property);

		// TODO - NG - get the currently logged in user and assign to the property
		// is there a UserProperty ref doc for this user?
		UserProperty userPropery = userPropertyRepo.findByUserEmail("");

		if (userPropery == null) {
			userPropery = createUserProperty("", property);
		}

		userPropertyRepo.save(userPropery);

		return property;
	}

	private UserProperty createUserProperty(String userEmail, Property property) {
		UserProperty p = new UserProperty();
		p.setUserEmail(userEmail);
		p.addProperty(property);
		return userPropertyRepo.save(p);
	}

	public Iterable<Property> getPropertyByUser(String userEmail) {
		LOGGER.trace("getPropertyByUser() : " + userEmail);

		UserProperty userProperty = userPropertyRepo.findByUserEmail(userEmail);

		if (userProperty == null) {
			throw new EntityNotFoundException("user.no.properties");
		}

		Iterable<Property> properties = propertyRepo.findAllById(convertIteratorToIterable(userProperty.getPropertyRefs().iterator()));

		return properties;
	}

	/**
	 * Quick easy Java 8 way to convert an Iterator into an Iterable
	 */
	private <T> Iterable<T> convertIteratorToIterable(Iterator<T> iter) {
		return () -> iter;
	}
}
