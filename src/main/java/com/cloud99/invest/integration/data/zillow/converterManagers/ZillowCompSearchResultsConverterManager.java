package com.cloud99.invest.integration.data.zillow.converterManagers;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.integration.data.DataProviderInfo;
import com.cloud99.invest.integration.data.zillow.domain.comps.ZillowComp;
import com.cloud99.invest.integration.data.zillow.domain.comps.ZillowComparablesResult;
import com.cloud99.invest.integration.data.zillow.messageConverters.ZillowCompConverter;
import com.cloud99.invest.integration.data.zillow.messageConverters.ZillowResultPropertyConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZillowCompSearchResultsConverterManager implements MessageConverterManager<PropertyCompSearchResult, ZillowComparablesResult> {

	private ZillowCompConverter compConverter = new ZillowCompConverter();
	private ZillowResultPropertyConverter propertyConverter = new ZillowResultPropertyConverter();

	@Override
	public PropertyCompSearchResult convert(Object incomingObj) {

		ZillowComparablesResult incoming = (ZillowComparablesResult) incomingObj;
		log.debug("Converting Zillow comp results to domain response");

		PropertyCompSearchResult result = new PropertyCompSearchResult();

		Collection<PropertyCompValuationResult> results = new ArrayList<>(incoming.getResponse().getProperties().getComparables().getComp().size());
		List<ZillowComp> comps = incoming.getResponse().getProperties().getComparables().getComp();

		// set the subject property on the result
		result.setSubjectProperty(propertyConverter.convert(incoming.getResponse().getProperties().getPrincipal()));

		for (ZillowComp comp : comps) {
			
			// convert the valuation
			PropertyCompValuationResult propertyCompResult = (PropertyCompValuationResult) compConverter.convert(comp);
			propertyCompResult.setProviderId(comp.getZpid());
			propertyCompResult.setProviderInfo(DataProviderInfo.ZILLOW);

			// convert the property
			Property property = propertyConverter.convert(comp);
			propertyCompResult.setProperty(property);
			results.add(propertyCompResult);

		}

		result.setPropertyValuations(results);
		return result;
	}

	@Override
	public Class<ZillowComparablesResult> getConvertFromType() {
		return ZillowComparablesResult.class;
	}

	@Override
	public Class<?> getConvertToType() {
		return PropertyCompSearchResult.class;
	}

}
