package com.cloud99.invest.services.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.services.calculations.Calculation.CalculationType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CapRateTest extends BaseCalculationsTest {

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.CAP_RATE;
	}

	@Override
	public double getLoanAmount() {
		return 300000D;
	}

	@Override
	public <T> void assertResult(T result) {
		assertEquals(new BigDecimal(0.0694).setScale(4, RoundingMode.HALF_EVEN), result);
	}

}
