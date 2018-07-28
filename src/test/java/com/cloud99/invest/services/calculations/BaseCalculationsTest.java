package com.cloud99.invest.services.calculations;

import com.cloud99.invest.BaseFinancialTest;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.services.calculations.Calculation.CalculationType;

import org.junit.jupiter.api.Test;

public abstract class BaseCalculationsTest extends BaseFinancialTest {

	@Test
	public void testCalculate() {

		PropertyFinances propertyFinances = buildPropertyFinances(getLoanAmount());

		Calculation<?> calculation = Calculation.ALL_CALCULATIONS.get(getCalculationType());
		assertResult(calculation.calculate(propertyFinances, Calculation.ALL_CALCULATIONS));
	}

	public abstract CalculationType getCalculationType();

	public abstract double getLoanAmount();

	public abstract <T> void assertResult(T result);

}
