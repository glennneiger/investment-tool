package com.cloud99.invest.services;

import com.cloud99.invest.domain.property.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);


	public <T extends Property> Property createProperty(T property) {
		LOGGER.debug("Starting to create a new property: " + property);
		return null;
	}
}
