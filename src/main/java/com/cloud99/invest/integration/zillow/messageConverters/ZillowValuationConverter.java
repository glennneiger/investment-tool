package com.cloud99.invest.integration.zillow.messageConverters;

import com.cloud99.invest.dto.responses.PropertyValuationResult;
import com.cloud99.invest.integration.MessageConverter;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.zillow.results.ZillowEstimate;
import com.cloud99.invest.util.Util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZillowValuationConverter implements MessageConverter<ZillowEstimate, PropertyValuationResult> {

	@Setter
	private Util util;

	public ZillowValuationConverter(Util util) {
		this.util = util;
	}
	/* (non-Javadoc)
	 * @see com.cloud99.invest.investmenttool.integration.providers.Message#convert(com.cloud99.invest.investmenttool.integration.zillow.results.SearchResults)
	 */
	@Override
	public PropertyValuationResult convert(ZillowEstimate zest) {
		log.trace("Converting Zillow PropertyValuationResult");

		PropertyValuationResult result = new PropertyValuationResult();
		result.setProviderInfo(ProviderInfo.ZILLOW);

		if (zest == null) {
			throw new RuntimeException("No results returned from zillow property search");
		}

		if (hasAmmount(zest)) {
			result.setCurrentEstimate(util.convertToMoney(zest.getAmount().getContent(), zest.getAmount().getCurrency()));
		}

		if (hasHighValuation(zest)) {
			result.setHighValue(util.convertToMoney(zest.getValuationRange().getHigh().getContent(),
					zest.getValuationRange().getHigh().getCurrency()));
		}

		if (hasLowValuation(zest)) {
			result.setLowValue(util.convertToMoney(zest.getValuationRange().getLow().getContent(),
					zest.getValuationRange().getLow().getCurrency()));
		}

		if (zest.getValueChange() != null) {
			result.setValueChange(util.convertToMoney(zest.getValueChange().getContent(), zest.getValueChange().getCurrency()));
		}

		if (zest.getPercentile() != null) {
			result.setPercentileChange(zest.getPercentile());
		}

		return result;
	}

	private boolean hasAmmount(ZillowEstimate zest) {
		return zest.getAmount() != null || zest.getAmount().getContent() != null;
	}

	private boolean hasLowValuation(ZillowEstimate zest) {
		return zest.getValuationRange().getLow() != null && zest.getValuationRange().getLow().getContent() != null;
	}

	private boolean hasHighValuation(ZillowEstimate zest) {
		return zest.getValuationRange().getHigh() != null && zest.getValuationRange().getHigh().getContent() != null;
	}

}
