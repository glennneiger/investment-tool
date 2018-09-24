package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.zillow.results.comps.Comp;
import com.cloud99.invest.integration.zillow.results.comps.ZillowComparables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

// TODO - NG - add the creation of this object to a factory class
@Slf4j
public class ZillowCompSearchResultsConverterManager {

	private ZillowCompConverter compConverter = new ZillowCompConverter();
	private ZillowResultPropertyConverter propertyConverter = new ZillowResultPropertyConverter();

	public PropertyCompSearchResult convert(ZillowComparables incoming) {
		log.debug("Converting Zillow comp results to domain response");

		PropertyCompSearchResult result = new PropertyCompSearchResult();

		Collection<PropertyCompValuationResult> results = new ArrayList<>(incoming.getResponse().getProperties().getComparables().getComp().size());
		List<Comp> comps = incoming.getResponse().getProperties().getComparables().getComp();

		// set the subject property on the result
		result.setSubjectProperty(propertyConverter.convert(incoming.getResponse().getProperties().getPrincipal()));

		for (Comp comp : comps) {
			
			// convert the valuation
			PropertyCompValuationResult propertyCompResult = compConverter.convert(comp);
			propertyCompResult.setProviderId(comp.getZpid());
			propertyCompResult.setProviderInfo(ProviderInfo.ZILLOW);

			// convert the property
			Property property = propertyConverter.convert(comp);
			propertyCompResult.setProperty(property);
			results.add(propertyCompResult);

		}

		result.setPropertyValuations(results);
		return result;
	}

}
