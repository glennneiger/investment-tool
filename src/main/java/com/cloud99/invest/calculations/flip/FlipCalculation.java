package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.CurrencyUnit;

import java.util.Map;

public interface FlipCalculation<RETURN_TYPE> {

	RETURN_TYPE calculate(FlipPropertyFinances propertyFinances, Map<FlipCalculationType, FlipCalculation<?>> allCalculations, CurrencyUnit currency);

}
