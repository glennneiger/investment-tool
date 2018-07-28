package com.cloud99.invest.services.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.Income;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.services.calculations.Calculation.CalculationType;

import org.junit.jupiter.api.BeforeEach;

class GrossYieldTest extends BaseCalculationsTest {

	@BeforeEach
	void setUp() throws Exception {
		super.setup();
	}

	@Override
	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {
		PurchaseDetails d = super.buildPurchaseDetails(purchasePrice, arv);
		d.setRehabCosts(buildItemizedCost(20000D));
		return d;
	}

	@Override
	public Income buildMonthlyIncome(double deposit, double rent, double otherIncome) {
		return super.buildMonthlyIncome(deposit, 750, otherIncome);
	}

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.GROSS_YIELD;
	}

	@Override
	public double getLoanAmount() {
		return 80000D;
	}

	@SuppressWarnings("boxing")
	@Override
	public <T> void assertResult(T result) {
		// Purchase costs: 100k
		// Annual rent: 9k
		assertEquals(.09D, result);
	}

}
