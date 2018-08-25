package com.cloud99.invest.services.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.Expences;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.services.calculations.Calculation.CalculationType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Cash Flow / Cash In Deal Cash Flow = (Net Operating Income – Debt Service)
 */
public class CashOnCashTest extends BaseCalculationsTest {

	@Override
	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {		
		PurchaseDetails d = super.buildPurchaseDetails(purchasePrice, arv);
		d.setClosingCosts(buildItemizedCost(5000D));
		return d;

	}

	@Override
	public Expences buildExpences(float vacancyRate, double operatingExpence) {
		// we want to add a vacancy rate of 2% to this calculation
		return super.buildExpences(.02F, operatingExpence);
	}

	@Override
	public CalculationType getCalculationType() {
		return CalculationType.CASH_ON_CASH;
	}

	@Override
	public double getLoanAmount() {
		return 300000D;
	}

	@Override
	public <T> void assertResult(T result) {
		// closing costs = 5,000
		// annual income = 45,000.00

		// annual debt service = 18,240.72
		// noi = 2,5000
		// vacancy = 900.00
		// cash flow = 6,759.28
		// cash in deal = 65000.00

		assertEquals(new BigDecimal(10.00D).setScale(2, RoundingMode.HALF_EVEN), result);

	}


}
