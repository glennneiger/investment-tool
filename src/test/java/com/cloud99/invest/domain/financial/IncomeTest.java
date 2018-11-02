package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.BaseFinancialTest;
import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.rental.RentalIncome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

class IncomeTest extends BaseFinancialTest {

	@InjectMocks
	private RentalIncome income;

	@BeforeEach
	void setUp() throws Exception {
		income.setGrossRent(new BigDecimal(2050));
		income.setOtherIncome(new BigDecimal(155));
		income.setDeposit(new BigDecimal(2000));
	}

	@Test
	public void testGetTotalAnnualOperatingIncome() {

		int periods = Frequency.MONTHY.getAnnualPeriods();
		double total = (2050 * periods) + (155 * periods);

		assertEquals(total, income.getTotalAnnualOperatingIncome(CURRENCY).getAmount().doubleValue());
	}

	@Test
	void testGetTotalIncome() {

		double total = 2050 + 155 + 2000;

		assertEquals(total, income.getTotalIncome(CURRENCY).getAmount().doubleValue());
	}

	@Test
	void testGetTotalOperatingIncome() {

		double total = 2050 + 155;

		assertEquals(total, income.getTotalOperatingIncome(CURRENCY).getAmount().doubleValue());
	}

}
