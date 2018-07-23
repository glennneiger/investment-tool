package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.TimeUnit;
import com.cloud99.invest.services.AnalyzerService;
import com.cloud99.invest.services.LoanService;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

public class AnalyzerServiceTest extends MockitoTest {

	@InjectMocks
	private AnalyzerService service;

	private LoanService loanService = new LoanService();

	@BeforeEach
	void setUp() throws Exception {
		service.setLoanService(loanService);
	}

	@Test
	public void testDebtServiceRatio() {

		PropertyFinances propertyFinances = buildPropertyFinances(300000D);

		double ratio = service.calculateDebtServiceRatio(propertyFinances);

		// debt = 18240.72
		// noi = 25000.00
		assertEquals(1.37D, ratio);

	}

	@Test
	public void testCalculateCashOnCashReturn() {

		PropertyFinances propertyFinances = buildPropertyFinances(300000D);
		double cashOnCash = service.calculateCashOnCash(propertyFinances);

		// debt = 18240.72
		// cash flow = 6759.28
		// cash in deal = 60000
		assertEquals(.11D, cashOnCash);
	}

	@Test
	void testCalculateNetOperatingIncome() {
		// purchase price: $250k
		PropertyFinances propertyFinances = buildPropertyFinances(250000D);

		Money noi = service.calculateNetOperatingIncome(propertyFinances);
		assertEquals(buildMoney(25000), noi);

	}

	@Test
	void calculateCapRate() {

		PropertyFinances propertyFinances = buildPropertyFinances(300000D);

		assertEquals(new Float(0.083333336), service.calculateCapRate(propertyFinances));

	}

	private PropertyFinances buildPropertyFinances(double purchasePrice) {

		// 20% Down, 4.5% Interest rate
		FinancingDetails details = buildFinancingDetails(purchasePrice, 0.20D, 4.5F);

		// rehab: $50k

		// annual operating expenses: $3000 - monthly: $20K - annually
		Expences expences = buildExpences(0F, 1666.666);

		// annual income: $45K - annually
		Income income = buildIncome(0, 3750, 0);

		// After repair value (ARV) - 315000
		PurchaseDetails purchaseDetails = buildPurchaseDetails(purchasePrice, 315000D);
		purchaseDetails.setFinancingDetails(details);

		PropertyFinances cash = new PropertyFinances(expences, income, purchaseDetails, CURRENCY);

		return cash;
	}

	private PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv ) {

		PurchaseDetails d = new PurchaseDetails();
		d.setAfterRepairValue(new BigDecimal(arv));
		d.setPurchasePrice(new BigDecimal(purchasePrice));
		
		return d;
	}

	private Income buildIncome(double deposit, double rent, double otherIncome) {

		Income i = new Income();
		i.setDeposit(new BigDecimal(deposit));
		i.setGrossRent(new BigDecimal(rent));
		i.setOtherIncome(new BigDecimal(otherIncome));
		i.setRentUnit(TimeUnit.MONTHY);

		return i;
	}

}
