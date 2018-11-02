package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;

import java.util.Map;

/**
 * Interface for all calculations utilized for rental analysis
 *
 * @param <RETURN_TYPE>
 *            - the java type for the return value: i.e BigDecimal, Money,
 *            Double, etc...
 */
public interface RentalCalculation<RETURN_TYPE> { // extends Calculation<RentalPropertyFinances, RETURN_TYPE> {

	public RETURN_TYPE calculate(RentalPropertyFinances propertyFinances, Map<RentalCalculationType, RentalCalculation<?>> allCalculations, CurrencyUnit currency);
}
