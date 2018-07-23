package com.cloud99.invest.services.calculations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.TimeUnit;
import com.cloud99.invest.domain.financial.Expences;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.Income;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.services.AnalyzerService;
import com.cloud99.invest.services.LoanService;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

// TODO - NG - refactor this into using the calculation strategies
public class AnalyzerServiceTest extends MockitoTest {

	@InjectMocks
	private AnalyzerService service;

	@BeforeEach
	void setUp() throws Exception {

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



}
