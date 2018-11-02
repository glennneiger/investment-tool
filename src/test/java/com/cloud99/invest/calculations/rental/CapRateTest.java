package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CapRateTest extends BaseRentalCalculationsTest {

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.CAP_RATE;
	}

	@Override
	public double getLoanAmount() {
		return 300000D;
	}

	@Override
	public <T> void assertResult(T result) {
		assertEquals(new BigDecimal(6.7568).setScale(4, RoundingMode.HALF_EVEN), result);
	}

}
