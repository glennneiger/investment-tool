package com.cloud99.invest.services;

import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.dto.requests.FlipAnalysisRequest;
import com.cloud99.invest.dto.responses.FlipAnalysisResults;
import com.cloud99.invest.integration.ProviderInfo;
import com.cloud99.invest.integration.ServiceProvider;
import com.cloud99.invest.integration.ServiceProviderFactory;
import com.cloud99.invest.util.Util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnalyzerService {

	@Getter
	@Setter
	private Util util;

	public FlipAnalysisResults analyzeFlip(FlipAnalysisRequest flipRequest) {

		log.debug("CalculateFile with assumptions: " + flipRequest);

		FlipAnalysisResults flipResults = new FlipAnalysisResults();

		BigDecimal maxAllowableOffer = calculateMaxOfferPrice(flipRequest);

		Money totalHoldingCosts = summarizeHoldingCosts(flipRequest.getHoldingCosts(), flipRequest.getHoldingDays(), flipRequest.getCurrencyUnit());
		log.debug("Holding costs: {}", totalHoldingCosts);

		Money totalCosts = util.summerizeItemizedCosts(flipRequest.getCurrencyUnit(), flipRequest.getClosingCosts()).plus(totalHoldingCosts);
		log.debug("Total costs: {}", totalCosts);

		flipResults.setMaxAllowableOfferPrice(Money.of(flipRequest.getCurrencyUnit(), maxAllowableOffer));

		Money totalOutOfPocket = totalCosts.plus(flipRequest.getRepairCosts());
		Double roi = flipRequest.getDesiredProfit().divide(totalOutOfPocket.getAmount(), 3, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(10), MathContext.DECIMAL32).doubleValue();
		log.debug("ROI: {}%", roi);
		flipResults.setReturnOnInvestment(roi);

		flipResults.setRateOfReturn(roi / (flipRequest.getHoldingDays() * Frequency.DAILY.getAnnualPeriods()));
		// TODO - NG - calculate hypothetical forecast

		return flipResults;
	}

	/**
	 * (ARV) – (Repair Costs) – (Closing and Holding Costs) – (Desired profit) =
	 * Offer Price
	 * 
	 * @return
	 */
	private BigDecimal calculateMaxOfferPrice(FlipAnalysisRequest flipRequest) {

		// calculate total holding costs
		BigDecimal totalHoldingCosts = util.summerizeItemizedCosts(flipRequest.getCurrencyUnit(), flipRequest.getHoldingCosts()).getAmount();
		BigDecimal totalClosingCosts = util.summerizeItemizedCosts(flipRequest.getCurrencyUnit(), flipRequest.getClosingCosts()).getAmount();

		//@formatter:off
		BigDecimal result = 
				flipRequest.getAfterRepairValue()
				.subtract(flipRequest.getRepairCosts())
				.subtract(totalHoldingCosts)
				.subtract(totalClosingCosts)
				.subtract(flipRequest.getDesiredProfit())
				.setScale(2);
		//@formatter:on

		log.debug("Max Allowable Offer: {}", result);
		return result;
	}

	/**
	 * The Rate of Return, is the ROI over a particular period of time. This number
	 * is useful to help you understand the effect of the deal on your overall
	 * business for that period of time. I typically measure it in terms of a year,
	 * and so the rate of return formula is: ROR = ROI/Holding Days * 365 For
	 * example say you have the following 2 oversimplified deals:
	 * 
	 * Deal 1: 90 day project, 20% ROI Deal 2: 180 day project, 30% ROI
	 * 
	 * The ROR on the first deal is 81% (20/90*365), and the ROR on the second deal
	 * is 61% (30/180*365). That means that even though the ROI for the first deal
	 * is significantly lower than the second, your business will grow by an extra
	 * 20% for the year, if you choose to go after deals like the first one. The
	 * minimum ROR that we look for in our flip deals is 30%
	 *
	 */
	private void calculateRateOfReturn() {
		// TODO - NG - implement this calculation and see about making it a Calculation
		// class instead
	}

	private Money summarizeHoldingCosts(Collection<ItemizedCost> costs, Integer holdDays, CurrencyUnit currencyUnit) {
		Money sum = util.summerizeItemizedCosts(currencyUnit, costs);
		// holdDays divided by 30 will give us the number of periods to multiply the sum
		// against
		return sum.multipliedBy(holdDays / 30);
	}

	// $20 per sq. ft for repair values

	// Purchase costs = .05% of purchase price

	// Selling costs = 5-6% (3% is typical)

	// Include 1% for other closing costs

}
