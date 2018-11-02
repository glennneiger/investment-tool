package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.rental.RentalIncome;

import org.junit.jupiter.api.BeforeEach;

public class RentCostTest extends BaseRentalCalculationsTest {

	@BeforeEach
	void setUp() throws Exception {
		super.setup();
	}

	@Override
	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {
		PurchaseDetails d = super.buildPurchaseDetails(purchasePrice, arv);
		d.setRehabCosts(buildItemizedCost(25000));
		return d;
	}

	@Override
	public RentalIncome buildMonthlyIncome(double deposit, double rent, double otherIncome) {
		return super.buildMonthlyIncome(deposit, 1000, otherIncome);
	}

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.RENT_COST;
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
		// 60k down payment
		assertEquals(1.67D, result);

	}


}
