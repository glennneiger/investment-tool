package com.cloud99.invest.services;

import com.cloud99.invest.dto.responses.CompAnalysisResult;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompAnalyzerService {

	@Autowired
	@Setter
	private Util util;

	@Autowired
	private AccountService accountService;

	public CompAnalysisResult compAnalysisResult(String accountId, PropertyCompSearchResult compRequest) {
		
		Integer subjectSqFt = compRequest.getSubjectProperty().getFinishedSqFt();
		log.debug("Subject property SQ FT: {}", subjectSqFt);
		Iterator<PropertyCompValuationResult> iter = compRequest.getPropertyValuations().iterator();

		PropertyCompValuationResult tmpResult = null;
		Integer count = 0;

		BigDecimal totalHighSqFtPrice = BigDecimal.valueOf(0);
		BigDecimal totalLowSqFtPrice = BigDecimal.valueOf(0);

		BigDecimal totalSqFtPrice = BigDecimal.valueOf(0);

		while (iter.hasNext()) {

			tmpResult = iter.next();
			BigDecimal sqFt = BigDecimal.valueOf(tmpResult.getProperty().getFinishedSqFt());
			totalSqFtPrice = totalSqFtPrice.add(tmpResult.getCurrentEstimate().getAmount().divide(sqFt, 2, RoundingMode.HALF_EVEN));
			totalHighSqFtPrice = totalHighSqFtPrice.add(tmpResult.getHighValue().getAmount().divide(sqFt, 2, RoundingMode.HALF_EVEN));
			totalLowSqFtPrice = totalLowSqFtPrice.add(tmpResult.getLowValue().getAmount().divide(sqFt, 2, RoundingMode.HALF_EVEN));

			count++;
		}

		CompAnalysisResult result = new CompAnalysisResult();
		result.setSearchResults(compRequest);

		CurrencyUnit currency = accountService.getAccountSettings(accountId).getCurrency();

		// calculate the averages
		result.setAverageHighPrice(Money.of(currency, totalHighSqFtPrice.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(subjectSqFt))));
		log.debug("Average high price: {}", result.getAverageHighPrice());

		result.setAverageLowPrice(Money.of(currency, totalLowSqFtPrice.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(subjectSqFt))));
		log.debug("Average low price: {}", result.getAverageLowPrice());

		BigDecimal avgSqFtPrice = totalSqFtPrice.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_EVEN);
		result.setAverageCurrentEstimate(Money.of(currency, avgSqFtPrice.multiply(BigDecimal.valueOf(subjectSqFt))));
		log.debug("Price valuation by per sq ft amount: " + result.getAverageCurrentEstimate());

		return result;
	}

}
