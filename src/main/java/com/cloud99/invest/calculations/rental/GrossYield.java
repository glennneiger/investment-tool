package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Annual Rent / Total Price of Property (Purchase price + rehab costs)
 */
@Slf4j
public class GrossYield implements RentalCalculation<Double> {

	@Override
	public Double calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency) {

		BigDecimal purchase = propertyFinances.getPurchaseDetails().getPurchasePrice().add(propertyFinances.getPurchaseDetails().getTotalRehabCosts(currency).getAmount());
		log.debug("Total Purchase Cost: " + purchase);
		
		Money rent = propertyFinances.getIncome().getAnnualRentalIncome(currency);
		log.debug("Annual Gross Rent: " + rent);
		
		BigDecimal yield = rent.getAmount().divide(purchase).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN);
		log.debug("Gross Yield: " + yield);

		return yield.doubleValue();
	}

}
