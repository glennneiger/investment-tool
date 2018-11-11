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

public class DebtServiceRatioTest extends BaseRentalCalculationsTest {

	@Override
	public void assertResult(Object result) {

		assertEquals(new BigDecimal(1.71).setScale(2, RoundingMode.HALF_EVEN), result);

	}

	@Override
	public RentalPropertyFinances buildRentalPropertyFinances() {
		Double salesPrice = 300000D;

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
	public RentalCalculationType getCalculationType() {
		return RentalCalculationType.DEBT_SERVICE_RATIO;
	}

}
