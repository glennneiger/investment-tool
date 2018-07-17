package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.repo.PropertyRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import java.util.Collection;
import java.util.Iterator;

@Service
public class PropertyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepo propertyRepo;
	
	public <T extends Property> T createProperty(T property) {
		LOGGER.trace("Starting to create a new property: " + property.toJsonString());

		property = propertyRepo.save(property);

		User user = userService.getCurrentSessionUser();
		userService.addPropertyRefToUser(user, property.getId());

		return property;
	}


	public Iterable<Property> getPropertyDetails(Collection<String> userPropertyRefs) {
		LOGGER.trace("getProperty() : " + userPropertyRefs);

		return propertyRepo.findAllById(convertIteratorToIterable(userPropertyRefs.iterator()));

	}

	public void deleteProperty(Property property) {

		propertyRepo.delete(property);
	}

	/**
	 * Quick easy Java 8 way to convert an Iterator into an Iterable
	 */
	private <T> Iterable<T> convertIteratorToIterable(Iterator<T> iter) {
		return () -> iter;
	}

	public void createFinancialPropertyData(PropertyFinances cashFlow) {
		// TODO Auto-generated method stub

	}

	public Property updateProperty(@Valid Property property) {
		return propertyRepo.save(property);
	}

}
