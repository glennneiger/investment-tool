package com.cloud99.invest.services;

import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.dto.requests.FlipAnalysisRequest;
import com.cloud99.invest.dto.responses.FlipAnalysisResults;
import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnalyzerService {

	@Autowired
	@Getter
	@Setter
	private Util util;

	public FlipAnalysisResults analyzeFlip(FlipAnalysisRequest flipAssumptions) {

		log.debug("CalculateFile with assumptions: " + flipAssumptions);
		FlipAnalysisResults flipResults = new FlipAnalysisResults();

		// (ARV) – (Repair Costs)
		BigDecimal arvMinusRepair = flipAssumptions.getAfterRepairValue().subtract(flipAssumptions.getRepairCosts());

		// minus (Closing and Holding Costs) – (Desired profit)
		Money totalHoldingCosts = summarizeHoldingCosts(flipAssumptions.getHoldingCosts(), flipAssumptions.getHoldingDays(), flipAssumptions.getCurrencyUnit());
		log.debug("Holding costs: {}", totalHoldingCosts);

		Money totalCosts = util.summerizeItemizedCosts(flipAssumptions.getCurrencyUnit(), flipAssumptions.getClosingCosts()).plus(totalHoldingCosts);
		log.debug("Total costs: {}", totalCosts);

		BigDecimal offerPrice = arvMinusRepair.subtract(totalCosts.getAmount()).subtract(flipAssumptions.getDesiredProfit());
		log.debug("Max offer price: {}", offerPrice);

		flipResults.setMaxAllowableOfferPrice(Money.of(flipAssumptions.getCurrencyUnit(), offerPrice));

		Money totalOutOfPocket = totalCosts.plus(flipAssumptions.getRepairCosts());
		Double roi = flipAssumptions.getDesiredProfit().divide(totalOutOfPocket.getAmount(), RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(10)).doubleValue();
		log.debug("ROI: {}%", roi);
		flipResults.setReturnOnInvestment(roi);

		flipResults.setRateOfReturn(roi / (flipAssumptions.getHoldingDays() * Frequency.DAILY.getAnnualPeriods()));
		// TODO - NG - calculate hypothetical forecast

		return flipResults;
	}

	private Money summarizeHoldingCosts(Collection<ItemizedCost> costs, Integer holdDays, CurrencyUnit currencyUnit) {
		Money sum = util.summerizeItemizedCosts(currencyUnit, costs);
		return sum.multipliedBy(holdDays / 30);
	}

	// $20 per sq. ft for repair values

	// Purchase costs = .05% of purchase price

	// Selling costs = 5-6% (3% is typical)

	// Include 1% for other closing costs

}
