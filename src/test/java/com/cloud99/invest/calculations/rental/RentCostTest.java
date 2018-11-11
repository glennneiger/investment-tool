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

public class RentCostTest extends BaseRentalCalculationsTest {

	@BeforeEach
	void setUp() throws Exception {
		super.setup();
	}

	@Override
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.RENT_COST;
	}

	@Override
	public RentalPropertyFinances buildRentalPropertyFinances() {
		Double salesPrice = 50000D;

		FinancingDetails details = buildFinancingDetails(salesPrice, 0, 4.5F);

		RentalExpences expences = buildExpences(0F, 0F);

		RentalIncome income = buildMonthlyIncome(0, 1000, 0);

		// After repair value (ARV) - 315000
		PurchaseDetails purchaseDetails = buildPurchaseDetails(salesPrice, 0D);

		// rehab: $25k
		purchaseDetails.setRehabCosts(Arrays.asList(new ItemizedCost("Rehab costs", new BigDecimal(25000D))));
		purchaseDetails.setFinancingDetails(details);

		RentalPropertyFinances cash = new RentalPropertyFinances(expences, income, purchaseDetails);

		return cash;
	}

	@Override
	public <T> void assertResult(T result) {
		// 50k purchase
		// 25K rehab
		// 1000 rent
		assertEquals(1.33D, result);

	}


}
