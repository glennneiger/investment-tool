package com.cloud99.invest.calculations.flip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joda.money.Money;

public class MaximumAllowableOfferCostBasedTest extends BaseFlipCalculationsTest {

	@Override
	public FlipCalculationType getCalculationType() {
		return FlipCalculationType.MAXIMUM_ALLOWABLE_OFFER_AMOUNT_COST_BASED;
	}

	@Override
	public <T> void assertResult(T result) {
		assertEquals(56900.00D, ((Money) result).getAmount().doubleValue());

	}

}
