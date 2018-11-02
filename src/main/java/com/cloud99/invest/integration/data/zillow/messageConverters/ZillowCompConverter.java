package com.cloud99.invest.integration.data.zillow.messageConverters;

import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.data.MessageConverter;
import com.cloud99.invest.integration.data.zillow.domain.comps.ZillowComp;

public class ZillowCompConverter implements MessageConverter<ZillowComp, PropertyCompValuationResult> {

	private ZillowZestimateConverter<PropertyValuationResult> valuationConverter = new ZillowZestimateConverter<>();

	@Override
	public PropertyCompValuationResult convert(ZillowComp incoming) {

		PropertyValuationResult valuation = valuationConverter.convert(incoming.getZestimate());
		PropertyCompValuationResult result = new PropertyCompValuationResult(valuation);
		result.setProviderMatchingPercent(incoming.getScore());

		return result;
	}
}
