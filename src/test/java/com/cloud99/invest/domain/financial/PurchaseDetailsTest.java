package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

class PurchaseDetailsTest extends MockitoTest {

	@InjectMocks
	private PurchaseDetails purchaseDetails;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetTotalPurchaseCost() {

		double downPayment = 20000;
		double loanAmt = 300000;
		purchaseDetails.setAfterRepairValue(new BigDecimal(325000));
		purchaseDetails.setFinancingDetails(buildFinancingDetails(loanAmt, downPayment, 4.5f));
		purchaseDetails.setItemizedClosingCosts(buildItemizedCost(550, 12475));
		assertEquals(550 + 12475 + loanAmt, purchaseDetails.getTotalPurchaseCost(CURRENCY));

	}

	public Collection<ItemizedCost> buildItemizedCost(double expence1, double expense2) {
		return Arrays.asList(
				new ItemizedCost("Expence1", new BigDecimal(expence1), TimeUnit.MONTHY),
				new ItemizedCost("Expence1", new BigDecimal(expence1), TimeUnit.MONTHY));
	}

}
