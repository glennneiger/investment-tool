package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joda.money.Money;

public class AnnualDebtServiceTest extends BaseRentalCalculationsTest {

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.ANNUAL_DEBT_SERVICE;
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
