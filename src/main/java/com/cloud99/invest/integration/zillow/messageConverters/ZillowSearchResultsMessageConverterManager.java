package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.zillow.results.ZillowResult;
import com.cloud99.invest.integration.zillow.results.ZillowSearchResults;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for coordinating and invoking all
 * {@link MessageConverter} to convert a {@link ZillowSearchResults} into the
 * API domain's {@link PropertySearchResult}
 *
 */
@Slf4j
public class ZillowSearchResultsMessageConverterManager implements MessageConverter<ZillowSearchResults, PropertySearchResult> {

	private ZillowAddressConverter addressConverter = new ZillowAddressConverter();
	private ZillowPropertyConverter<? extends Property> propertyConverter = new ZillowPropertyConverter();

	@Override
	public PropertySearchResult convert(ZillowSearchResults result, Class<PropertySearchResult> returnClass) {
		log.trace("Converting Zillow PropertySearchResult");

		PropertySearchResult returnVal = null;
		try {
			returnVal = returnClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("Error creating new instance of class: {}", returnClass.getName());
			return null;
		}
		returnVal.setProviderInfo(ProviderInfo.ZILLOW);

		ZillowResult zillowResult = result.getResponse().getResults().getZillowResult();
		returnVal.setProviderId(zillowResult.getZpid());

		Property property = propertyConverter.convert(zillowResult, null);
		returnVal.setProperty(property);

		property.setAddress(addressConverter.convert(zillowResult.getAddress()));

		return returnVal;
	}

}
