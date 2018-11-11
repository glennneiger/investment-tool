package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.BaseFinancialTest;
import com.cloud99.invest.calculations.CalculationType;
import com.cloud99.invest.domain.financial.rental.RentalExpences;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRentalCalculationsTest extends BaseFinancialTest {

	@Test
	public void testCalculate() throws Exception {

		Map<RentalCalculationType, RentalCalculation<?>> map = buildMap();

		RentalPropertyFinances propertyFinances = buildRentalPropertyFinances();

		RentalCalculation<?> calculation = map.get(getCalculationType());
		assertResult(calculation.calculate(propertyFinances, map, CurrencyUnit.USD));
	}

	private Map<RentalCalculationType, RentalCalculation<?>> buildMap() throws Exception {
		Map<RentalCalculationType, RentalCalculation<?>> map = new HashMap<>();
		RentalCalculationType.ALL_CALCULATIONS.forEach(c -> {
			try {
				map.put(c, c.getCalcClass().newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return map;
	}

	public abstract RentalPropertyFinances buildRentalPropertyFinances();

	public RentalExpences buildExpences(float vacancyRate, double operatingExpence) {

		RentalExpences e = new RentalExpences();
		e.setOperatingExpences(buildReoccuringCost(operatingExpence));
		e.setVacancyRate(vacancyRate);

		return e;
	}

	public abstract CalculationType getCalculationType();

	public abstract <T> void assertResult(T result);

}
