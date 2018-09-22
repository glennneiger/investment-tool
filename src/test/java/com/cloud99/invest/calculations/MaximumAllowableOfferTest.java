package com.cloud99.invest.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.calculations.Calculation.CalculationType;

import org.joda.money.Money;

public class MaximumAllowableOfferTest extends BaseCalculationsTest {

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.MAXIMUM_ALLOWABLE_OFFER_AMOUNT;
	}

	@Override
	public double getLoanAmount() {
		return 0;
	}

	@Override
	public <T> void assertResult(T result) {
		// ARV = 315000 * .7 - 10000
		assertEquals(210500.00D, ((Money) result).getAmount().doubleValue());

	}

}
