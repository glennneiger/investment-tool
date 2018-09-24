package com.cloud99.invest.services;

import com.cloud99.invest.dto.responses.CompAnalysisResult;
import com.cloud99.invest.dto.responses.PropertyCompSearchResult;
import com.cloud99.invest.dto.responses.PropertyCompValuationResult;
import com.cloud99.invest.util.Util;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompAnalyzerService {

	@Autowired
	private Util util;

	public CompAnalysisResult compAnalysisResult(PropertyCompSearchResult compResult) {
		// get the SQ FT of the subject property
		Integer subjectSqFt = compResult.getSubjectProperty().getFinishedSqFt();
		log.debug("Subject property SQ FT: {}", subjectSqFt);
		Iterator<PropertyCompValuationResult> iter = compResult.getPropertyValuations().iterator();

		PropertyCompValuationResult tmpResult = null;
		Integer totalSqFt = 0;
		Integer count = 0;
		Money totalHighPrice = util.initializeNewMoney(compResult.getCurrencyUnit());
		Money totalLowPrice = util.initializeNewMoney(compResult.getCurrencyUnit());
		Money totalCurrentEstimates = util.initializeNewMoney(compResult.getCurrencyUnit());

		while (iter.hasNext()) {

			tmpResult = iter.next();
			totalSqFt += tmpResult.getProperty().getFinishedSqFt();
			totalHighPrice = totalHighPrice.plus(tmpResult.getHighValue());
			totalLowPrice = totalLowPrice.plus(tmpResult.getLowValue());
			totalCurrentEstimates = totalCurrentEstimates.plus(tmpResult.getCurrentEstimate());

			count++;
		}

		CompAnalysisResult result = new CompAnalysisResult();
		result.setSearchResults(compResult);

		// calculate the averages
		result.setAverageHighPrice(totalHighPrice.dividedBy(count, RoundingMode.HALF_EVEN));
		log.debug("Average high price: {}", result.getAverageHighPrice());

		result.setAverageLowPrice(totalLowPrice.dividedBy(count, RoundingMode.HALF_EVEN));
		log.debug("Average low price: {}", result.getAverageLowPrice());

		result.setAverageCurrentEstimate(totalCurrentEstimates.dividedBy(count, RoundingMode.HALF_EVEN));
		log.debug("Average current price: {}", result.getAverageCurrentEstimate());

		result.setAverageSqFt(totalSqFt / count);
		log.debug("Average sq ft: {}", result.getAverageSqFt());

		// home value based on sq ft average of comps
		result.setAveragePricePerSqFt(result.getAverageCurrentEstimate().dividedBy(result.getAverageSqFt(), RoundingMode.HALF_EVEN));
		log.debug("Average price per sq ft: {}", result.getAveragePricePerSqFt());

		result.setSubjectHomeValuationPricePerSqFt(result.getAveragePricePerSqFt().multipliedBy(subjectSqFt));
		System.out.println("Price valuation by per sq ft amount: " + result.getSubjectHomeValuationPricePerSqFt());

		return result;
	}

}
