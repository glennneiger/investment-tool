package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.CurrencyUnit;

import java.util.Map;

public class RateOfReturn implements FlipCalculation<Object>{

	@Override
	public Object calculate(FlipPropertyFinances propertyFinances, Map<FlipCalculationType, FlipCalculation<?>> allCalculations, CurrencyUnit currency) {
		// roi
		// allCalculations.get(FlipCalculationType.)
		return null;
	}

	
}
