package com.cloud99.invest.integration.zillow;

import com.cloud99.invest.domain.Address;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.domain.property.SingleFamilyProperty;
import com.cloud99.invest.dto.PropertyValuationResult;
import com.cloud99.invest.integration.MessageAdaptor;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.zillow.results.SearchResults;
import com.cloud99.invest.integration.zillow.results.Zestimate;
import com.cloud99.invest.util.Util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ZillowSearchProviderMessageAdaptor implements MessageAdaptor<PropertyValuationResult, SearchResults> {

	@Setter
	private Util util;

	/* (non-Javadoc)
	 * @see com.cloud99.invest.investmenttool.integration.providers.Message#convert(com.cloud99.invest.investmenttool.integration.zillow.results.SearchResults)
	 */
	@Override
	public PropertyValuationResult convert(SearchResults incoming) {

		PropertyValuationResult result = new PropertyValuationResult();
		result.setProviderInfo(ProviderInfo.ZILLOW);

		if (incoming == null || incoming.getResponse() == null || incoming.getResponse().getResults() == null
				|| incoming.getResponse().getResults().getResult() == null) {
			throw new RuntimeException("No results returned from zillow property search");
		}
		result.setProperty(convertProperty(incoming.getResponse().getResults().getResult()));

		result.setProviderId(incoming.getResponse().getResults().getResult().getZpid());

		Zestimate zest = incoming.getResponse().getResults().getResult().getZestimate();
		if (zest.getAmount() != null || zest.getAmount().getContent() != null) {
			result.setCurrentEstimate(util.convertToMoney(zest.getAmount().getContent(), zest.getAmount().getCurrency()));
		}

		if (zest.getValuationRange().getHigh() != null && zest.getValuationRange().getHigh().getContent() != null) {
			result.setHighValue(util.convertToMoney(zest.getValuationRange().getHigh().getContent(),
					zest.getValuationRange().getHigh().getCurrency()));
		}

		if (zest.getValuationRange().getLow() != null && zest.getValuationRange().getLow().getContent() != null) {
			result.setLowValue(util.convertToMoney(zest.getValuationRange().getLow().getContent(),
					zest.getValuationRange().getLow().getCurrency()));
		}

		if (zest.getValueChange() != null) {
			result.setValueChange(util.convertToMoney(zest.getValueChange().getContent(), zest.getValueChange().getCurrency()));
		}

		if (zest.getPercentile() != null) {
			result.setPercentileChange(Float.valueOf(zest.getPercentile()));
		}

		return result;
	}



	private Property convertProperty(com.cloud99.invest.integration.zillow.results.Result addr) {

		SingleFamilyProperty p = new SingleFamilyProperty();
		
		p.setAddress(buildAddress(addr.getAddress()));
		
		return p;

	}

	private Address buildAddress(com.cloud99.invest.integration.zillow.results.Address address) {

		return new Address(address.getStreet(), address.getCity(), address.getState(), address.getZipcode());
	}

}
