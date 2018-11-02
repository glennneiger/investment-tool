package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DebtServiceRatioTest extends BaseRentalCalculationsTest {

	@Override
	public void assertResult(Object result) {

		// debt = 18240.72
		// noi = 25000.00
		assertEquals(new BigDecimal(137.00).setScale(2, RoundingMode.HALF_EVEN), result);

	}

	@Override
	public double getLoanAmount() {
		return 300000D;
	}

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.DEBT_SERVICE_RATIO;
	}

}
