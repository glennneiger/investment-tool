package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.PropertyType;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.zillow.results.ZillowResult;
import com.cloud99.invest.integration.zillow.results.ZillowSearchResults;
import com.cloud99.invest.util.Util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for coordinating and invoking all
 * {@link MessageConverter} to convert a {@link ZillowSearchResults} into the
 * API domain's {@link PropertySearchResult}
 *
 */
@Slf4j
public class ZillowMessageConverterManager {

	private ZillowAddressConverter addressConverter = new ZillowAddressConverter();
	private ZillowPropertyConverter propertyConverter = new ZillowPropertyConverter();

	public PropertySearchResult convert(ZillowSearchResults result) {
		log.trace("Converting Zillow PropertySearchResult");

		PropertySearchResult returnVal = new PropertySearchResult();
		returnVal.setProviderInfo(ProviderInfo.ZILLOW);

		ZillowResult zillowResult = result.getResponse().getResults().getZillowResult();

		Property property = propertyConverter.convert(zillowResult);
		returnVal.setProperty(property);

		property.setAddress(addressConverter.convert(zillowResult.getAddress()));

		return returnVal;
	}

}
