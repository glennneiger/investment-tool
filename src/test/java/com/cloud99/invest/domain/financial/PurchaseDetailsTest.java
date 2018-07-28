package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.BaseFinancialTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

class PurchaseDetailsTest extends BaseFinancialTest {

	@InjectMocks
	private PurchaseDetails purchaseDetails;

	@Test
	void testGetTotalPurchaseCost() {

		double downPayment = 20000;
		double loanAmt = 300000;
		double rehabCosts = 15000;
		double closingCost1 = 550;
		double closingCost2 = 12475;
		purchaseDetails.setPurchasePrice(new BigDecimal(loanAmt));
		purchaseDetails.setFinancingDetails(buildFinancingDetails(loanAmt, downPayment, 4.5f));
		purchaseDetails.setItemizedClosingCosts(buildMonthlyItemizedCost(closingCost1, closingCost2));
		purchaseDetails.setRehabCosts(buildItemizedCost(rehabCosts));
		BigDecimal expected = new BigDecimal(loanAmt + closingCost1 + closingCost2 + rehabCosts + downPayment).setScale(2, RoundingMode.HALF_EVEN);
		assertEquals(expected, purchaseDetails.getTotalPurchaseCost(CURRENCY).getAmount());

	}

}
