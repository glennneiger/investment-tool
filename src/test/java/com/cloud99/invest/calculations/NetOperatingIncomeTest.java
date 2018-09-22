package com.cloud99.invest.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.calculations.Calculation.CalculationType;

public class NetOperatingIncomeTest extends BaseCalculationsTest {

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.NOI;
	}

	@Override
	public double getLoanAmount() {
		return 250000D;
	}

	@Override
	public <T> void assertResult(T result) {
		assertEquals(buildMoney(25000), result);
	}

}
