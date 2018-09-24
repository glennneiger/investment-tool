package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.zillow.results.comps.Comp;

public class ZillowCompConverter implements MessageConverter<Comp, PropertyCompValuationResult> {

	private ZillowZestimateConverter valuationConverter = new ZillowZestimateConverter();

	@SuppressWarnings("cast")
	@Override
	public PropertyCompValuationResult convert(Comp incoming) {

		PropertyCompValuationResult valuation = (PropertyCompValuationResult) valuationConverter.convert(incoming.getZestimate(), PropertyCompValuationResult.class);
		valuation.setProviderMatchingPercent(incoming.getScore());

		return valuation;
	}
}
