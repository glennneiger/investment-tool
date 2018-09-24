package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.property.Property;
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
	private ZillowResultPropertyConverter propertyConverter = new ZillowResultPropertyConverter();

	@Override
	public PropertySearchResult convert(ZillowSearchResults incoming) {
		log.trace("Converting Zillow PropertySearchResult");

		ZillowResult zillowResult = incoming.getResponse().getResults().getZillowResult();

		PropertySearchResult returnVal = new PropertySearchResult();
		returnVal.setProviderId(zillowResult.getZpid());
		returnVal.setProviderInfo(ProviderInfo.ZILLOW);

		Property property = propertyConverter.convert(zillowResult);
		returnVal.setProperty(property);

		property.setAddress(addressConverter.convert(zillowResult.getAddress()));

		return returnVal;
	}


}
