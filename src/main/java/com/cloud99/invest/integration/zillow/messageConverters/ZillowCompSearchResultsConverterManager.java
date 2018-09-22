package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.dto.responses.PropertyCompResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.zillow.results.comps.Comp;
import com.cloud99.invest.integration.zillow.results.comps.ZillowComparables;
import com.cloud99.invest.util.Util;

import java.util.ArrayList;
import java.util.Collection;

// TODO - NG - add the creation of this object to a factory class
public class ZillowCompSearchResultsConverterManager {

	private Util util;

	private ZillowPropertyConverter propertyConverter = new ZillowPropertyConverter();
	private ZillowCompConverter compConverter = new ZillowCompConverter();

	public ZillowCompSearchResultsConverterManager(Util util) {
		this.util = util;
	}

	public Collection<PropertyCompResult> convert(ZillowComparables result) {
		PropertyCompResult resultVal = new PropertyCompResult();
		Collection<PropertyCompResult> returnList = new ArrayList<>();

		PropertyCompResult returnVal = new PropertyCompResult();
		returnVal.setProviderInfo(ProviderInfo.ZILLOW);
		returnVal.setProviderId(result.getRequest().getZpid());

		Collection<PropertyCompResult> results = new ArrayList<>(result.getResponse().getProperties().getComparables().getComp().size());

		for (Comp comp : result.getResponse().getProperties().getComparables().getComp()) {
			PropertyCompResult propertyValuationResult = compConverter.convert(comp.getZestimate());
			propertyValuationResult.setProviderId(comp.getZpid());
			propertyValuationResult.setProviderInfo(ProviderInfo.ZILLOW);
			propertyValuationResult.setProviderMatchingPercent(comp.getScore());
			results.add(propertyValuationResult);

			Property subjectProperty = compConverter.convert(comp);
			propertyValuationResult.setSubjectProperty(subjectProperty);

		}

		return results;
	}

}
