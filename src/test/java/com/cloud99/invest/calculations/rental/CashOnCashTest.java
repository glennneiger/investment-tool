package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.rental.RentalExpences;
import com.cloud99.invest.domain.financial.rental.RentalIncome;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Cash Flow / Cash In Deal Cash Flow = (Net Operating Income – Debt Service)
 */
public class CashOnCashTest extends BaseRentalCalculationsTest {

	@Override
	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {		
		PurchaseDetails d = super.buildPurchaseDetails(purchasePrice, arv);

		return d;

	}

	@Override
	public RentalExpences buildExpences(float vacancyRate, double operatingExpence) {
		// we want to add a vacancy rate of 2% to this calculation
		return super.buildExpences(vacancyRate, operatingExpence);
	}

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.CASH_ON_CASH;
	}

	@Override
	public RentalPropertyFinances buildRentalPropertyFinances() {
		Double salesPrice = 40000D;

		// 20% Down ($60000K), 4.5% Interest rate
		FinancingDetails details = buildFinancingDetails(salesPrice - 60000, 0.20D * salesPrice, 4.5F);

		// annual operating expenses: $2710
		RentalExpences expences = buildExpences(0F, 225.834);

		// annual income: $9k - annually
		RentalIncome income = buildMonthlyIncome(0, 750, 0);

		PurchaseDetails purchaseDetails = buildPurchaseDetails(salesPrice, 0);

		// $5k closing costs
		purchaseDetails.setClosingCosts(buildItemizedCost(0));

		// rehab: $0k
		purchaseDetails.setRehabCosts(Arrays.asList(new ItemizedCost("Rehab costs", new BigDecimal(0D))));
		purchaseDetails.setFinancingDetails(details);

		RentalPropertyFinances cash = new RentalPropertyFinances(expences, income, purchaseDetails);

		return cash;
	}

	@Override
	public <T> void assertResult(T result) {

		// closing costs = 5,000
		// annual income = 45,000.00

		// annual debt service = 18,240.72
		// noi = 25,000
		// vacancy = 900.00
		// cash flow = 6,759.28
		// down payment = 60000.00
		// cash in deal = 65000.00

		assertEquals(new BigDecimal(15.70D).setScale(2, RoundingMode.HALF_EVEN), result);

	}


}
