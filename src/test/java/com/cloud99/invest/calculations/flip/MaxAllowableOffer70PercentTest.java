package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.junit.jupiter.api.BeforeEach;

class MaxAllowableOffer70PercentTest extends BaseFlipCalculationsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Override
	public FlipPropertyFinances getFlipPropertyFinances() {

		return null;
	}

	@Override
	public FlipCalculationType getCalculationType() {
		return FlipCalculationType.MAXIMUM_ALLOWABLE_OFFER_AMOUNT_70_PERCENT;
	}

	@Override
	public <T> void assertResult(T result) {
		// TODO Auto-generated method stub

	}

}
