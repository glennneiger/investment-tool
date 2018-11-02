package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Monthly Rent / Total Price of Property (purchase price + rehab costs)
 */
@Slf4j
public class RentCost implements RentalCalculation<Double> {

	@Override
	public Double calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		BigDecimal rent = propertyFinances.getIncome().getGrossRent();
		log.debug("Monthly Rent: " + rent);

		BigDecimal purchasePrice = propertyFinances.getPurchaseDetails().getPurchasePrice();
		log.debug("Purchase Price: " + purchasePrice);

		Money rehab = propertyFinances.getPurchaseDetails().getTotalRehabCosts(currency);
		log.debug("Rehab Costs: " + rehab);

		Money expences = rehab.plus(purchasePrice);
		log.debug("Total Expences: " + expences);

		BigDecimal rentCost = rent.divide(expences.getAmount(), 4, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100));
		log.debug("Rent Cost: " + rentCost);

		return rentCost.doubleValue();
	}

}
