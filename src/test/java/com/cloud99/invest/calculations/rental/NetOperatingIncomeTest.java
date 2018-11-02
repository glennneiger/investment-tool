package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetOperatingIncomeTest extends BaseRentalCalculationsTest {

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.NOI;
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
