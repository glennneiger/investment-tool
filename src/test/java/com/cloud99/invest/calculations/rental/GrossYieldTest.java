package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.rental.RentalIncome;

import org.junit.jupiter.api.BeforeEach;

class GrossYieldTest extends BaseRentalCalculationsTest {

	@BeforeEach
	void setUp() throws Exception {
		super.setup();
	}

	@Override
	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {
		PurchaseDetails d = super.buildPurchaseDetails(purchasePrice, arv);
		d.setRehabCosts(buildItemizedCost(20000));
		return d;
	}

	@Override
	public RentalIncome buildMonthlyIncome(double deposit, double rent, double otherIncome) {
		return super.buildMonthlyIncome(deposit, 750, otherIncome);
	}

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.GROSS_YIELD;
	}

	@Override
	public double getLoanAmount() {
		return 80000D;
	}

	@Override
	public <T> void assertResult(T result) {
		// Purchase costs: 100k
		// Annual rent: 9k
		// Down payment: 60k
		assertEquals(10.00D, result);
	}

}
