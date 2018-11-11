package com.cloud99.invest.calculations.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.rental.RentalExpences;
import com.cloud99.invest.domain.financial.rental.RentalIncome;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.Arrays;

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
	public RentalPropertyFinances buildRentalPropertyFinances() {
		Double salesPrice = 80000D;

		// 20% Down ($60000K), 4.5% Interest rate
		FinancingDetails details = buildFinancingDetails(salesPrice - 60000, 0.20D * 300000, 4.5F);

		// annual operating expenses: $3000 - monthly, $20K - annually
		RentalExpences expences = buildExpences(0F, 1666.67);

		// annual income: $45K - annually
		RentalIncome income = buildMonthlyIncome(0, 3750, 0);

		// After repair value (ARV) - 315000
		PurchaseDetails purchaseDetails = buildPurchaseDetails(salesPrice, 315000);

		// rehab: $10k
		purchaseDetails.setRehabCosts(Arrays.asList(new ItemizedCost("Rehab costs", new BigDecimal(10000D))));
		purchaseDetails.setFinancingDetails(details);

		RentalPropertyFinances cash = new RentalPropertyFinances(expences, income, purchaseDetails);

		return cash;
	}
	@Override
	public <T> void assertResult(T result) {
		// Purchase costs: 100k
		// Annual rent: 9k
		// Down payment: 60k
		assertEquals(10.00D, result);
	}

}
