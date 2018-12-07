package com.cloud99.invest.services;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class FlipAnalyzrServiceTest extends MockitoTest {

	private FlipAnalyzrService analyzerService = new FlipAnalyzrService();
	private AccountService accountServiceMock = mock(AccountService.class);

	@BeforeEach
	public void setup() {
		super.setup();
		analyzerService.setUtil(util);
		analyzerService.setAccountService(accountServiceMock);
	}

	@Test
	public void testCalculateFlip() {

		String acctId = "1";
		Mockito.when(accountServiceMock.getAccountSettings(acctId)).thenReturn(dataCreator.buildAccountSettings());
		// Collection<FlipCalculation<?>> results = analyzerService.analyzeFlip(acctId,
		// buildFinancials());

		// assertEquals(BigDecimal.valueOf(389400.00).setScale(2),
		// results.getMaxAllowableOfferPrice().getAmount());
		// assertEquals(Double.valueOf(5), results.getReturnOnInvestment());
	}

	private FlipPropertyFinances buildFinancials() {
		FlipPropertyFinances c = new FlipPropertyFinances();

		c.setAfterRepairValue(BigDecimal.valueOf(135000.00));
		c.setDesiredProfit(BigDecimal.valueOf(25000.00));
		c.setHoldingCosts(dataCreator.buildCosts(10000));
		c.setSaleClosingCosts(dataCreator.buildCosts(5000));
		PurchaseDetails pd = dataCreator.buildPurchaseDetails(null);
		pd.setClosingCosts(dataCreator.buildCosts(13100));
		pd.setRehabCosts(dataCreator.buildCosts(25000));
		c.setPurchaseDetails(pd);
		c.setHoldingDays(60);

		return c;
	}
}
