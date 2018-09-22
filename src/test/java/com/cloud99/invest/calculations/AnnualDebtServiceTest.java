package com.cloud99.invest.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.calculations.Calculation.CalculationType;

import org.joda.money.Money;

public class AnnualDebtServiceTest extends BaseCalculationsTest {

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.ANNUAL_DEBT_SERVICE;
	}

	@Override
	public double getLoanAmount() {
		return 300000;
	}

	@Override
	public <T> void assertResult(T result) {
		Money expected = Money.of(CURRENCY, (1520.06D * 12));
		assertEquals(expected, result);

	}

}
