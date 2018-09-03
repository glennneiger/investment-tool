package com.cloud99.invest.services.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.Income;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.services.calculations.Calculation.CalculationType;

import org.junit.jupiter.api.BeforeEach;

public class RentCostTest extends BaseCalculationsTest {

	@BeforeEach
	void setUp() throws Exception {
		super.setup();
	}

	@Override
	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {
		PurchaseDetails d = super.buildPurchaseDetails(purchasePrice, arv);
		d.setRehabCosts(buildItemizedCost(25000D));
		return d;
	}

	@Override
	public Income buildMonthlyIncome(double deposit, double rent, double otherIncome) {
		return super.buildMonthlyIncome(deposit, 1000, otherIncome);
	}

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.RENT_COST;
	}

	@Override
	public double getLoanAmount() {
		return 50000;
	}

	@Override
	public <T> void assertResult(T result) {
		// 50k purchase
		// 25K rehab
		// 1000 rent
		assertEquals(1.33D, result);

	}


}
