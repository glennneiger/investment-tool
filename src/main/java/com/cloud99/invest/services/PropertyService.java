package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.repo.PropertyFinancesRepo;
import com.cloud99.invest.repo.PropertyRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Service
public class PropertyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepo propertyRepo;
	
	@Autowired
	private PropertyFinancesRepo financesRepo;

	public <T extends Property> T createProperty(T property) {
		LOGGER.debug("Starting to create a new property: " + property.toJsonString());

		property = (T) propertyRepo.save(property);

		User user = userService.getCurrentSessionUser();
		user.setPropertyRefs(new ArrayList<>());

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

	public Property updateProperty(@Valid Property property) {
		return propertyRepo.save(property);
	}

	public PropertyFinances createPropertyFinances(String propertyId, @Valid PropertyFinances propFinances) {

		Optional<Property> prop = propertyRepo.findById(propertyId);
		if (!prop.isPresent()) {
			throw new EntityNotFoundException("Property ID", propertyId);
		} else {
			Property property = prop.get();
			property.setPropertyFinances(propFinances);
			propertyRepo.save(property);
			return property.getPropertyFinances();
		}

	}

	public PropertyFinances getPropertyFinancials(String propertyId) {

		Optional<PropertyFinances> optional = financesRepo.findById(propertyId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("Property Finances", propertyId);
		}
		return optional.get();
	}

	/**
	 * Quick easy Java 8 way to convert an Iterator into an Iterable
	 */
	private <T> Iterable<T> convertIteratorToIterable(Iterator<T> iter) {
		return () -> iter;
	}

	public void deleteProperty(String propertyId) {
		propertyRepo.deleteById(propertyId);
	}

	public Property getProperty(String propertyId) {

		Optional<Property> optional = propertyRepo.findById(propertyId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("Property", propertyId);
		}
		return optional.get();

	}
}
