package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * A cap rate is a calculation used to determine the profitability of a real
 * estate investment
 * 
 * Cap Rate = (NOI / Total Purchase Price [including rehab costs])
 */
@Slf4j
public class CapRate implements RentalCalculation<BigDecimal> {

	@Override
	public BigDecimal calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		Money noi = (Money) allCalculations.get(RentalCalculationType.NOI).calculate(propertyFinances, allCalculations, currency);
		log.debug("NOI: " + noi);

		Float purchasePrice = propertyFinances.getPurchaseDetails().getTotalPurchaseCost(currency).getAmount().floatValue();
		log.debug("Purchase Price: " + purchasePrice);

		BigDecimal capRate = BigDecimal.valueOf(noi.getAmount().doubleValue() / purchasePrice * 100).setScale(4, RoundingMode.HALF_EVEN);
		log.debug("Cap Rate: " + capRate);

		return capRate;

	}
}
