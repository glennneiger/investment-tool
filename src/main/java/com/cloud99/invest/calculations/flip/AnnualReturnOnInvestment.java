package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Return on Investment (ROI) is a performance measure, used to evaluate the
 * efficiency of an investment or compare the efficiency of a number of
 * different investments. ROI measures the amount of return on an investment,
 * relative to the investment’s cost. To calculate ROI, the benefit (or return)
 * of an investment is divided by the cost of the investment. The result is
 * expressed as a percentage or a ratio.
 *
 * ROI = (Gain from Investment - Cost of Investment) / Cost of Investment Divide
 * the dollar amount of the return by the total dollar amount out of pocket for
 * the investment.
 */
@Slf4j
public class AnnualReturnOnInvestment implements FlipCalculation<BigDecimal> {

	@Override
	public BigDecimal calculate(FlipPropertyFinances propertyFinances, Map<FlipCalculationType, FlipCalculation<?>> allCalculations, CurrencyUnit currency) {
		
		// calculate total out of pocket expenses 
		Money totalCosts = propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(currency);
		// desired profit *
		BigDecimal roi = propertyFinances.getDesiredProfit().divide(totalCosts.getAmount());
		log.debug("ROI: {}", roi);
		return roi;
	}
}
