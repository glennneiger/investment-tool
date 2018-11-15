package com.cloud99.invest.integration.data.zillow.converterManagers;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.responses.PropertySearchResult;
import com.cloud99.invest.integration.data.DataProviderInfo;
import com.cloud99.invest.integration.data.zillow.domain.ZillowApiResult;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResult;
import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResults;
import com.cloud99.invest.integration.data.zillow.messageConverters.ZillowAddressConverter;
import com.cloud99.invest.integration.data.zillow.messageConverters.ZillowResultPropertyConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for coordinating and invoking all
 * {@link MessageConverter} to convert a {@link ZillowSearchResults} into the
 * API domain's {@link PropertySearchResult}
 *
 */
@Slf4j
public class ZillowSearchResultsMessageConverterManager implements MessageConverterManager<PropertySearchResult, ZillowSearchResults> {

	private ZillowAddressConverter addressConverter = new ZillowAddressConverter();
	private ZillowResultPropertyConverter propertyConverter = new ZillowResultPropertyConverter();

	@Override
	public PropertySearchResult convert(Object incomingObj) {

		log.trace("Converting Zillow PropertySearchResult");
		ZillowSearchResults incoming = (ZillowSearchResults) incomingObj;

		ZillowSearchResult zillowResult = incoming.getResponse().getResults().getZillowResult();

		PropertySearchResult returnVal = new PropertySearchResult();
		returnVal.setProviderId(zillowResult.getZpid());
		returnVal.setProviderInfo(DataProviderInfo.ZILLOW);

		Property property = propertyConverter.convert(zillowResult);
		returnVal.setProperty(property);

		property.setAddress(addressConverter.convert(zillowResult.getAddress()));

		return returnVal;
	}

	@Override
	public Class<? extends ZillowApiResult> getConvertFromType() {
		return ZillowSearchResults.class;
	}

	@Override
	public Class<?> getConvertToType() {
		return PropertySearchResult.class;
	}

}
