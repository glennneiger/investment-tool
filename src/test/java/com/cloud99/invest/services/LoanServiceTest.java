package com.cloud99.invest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.MockitoTest;

import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

public class LoanServiceTest extends MockitoTest {

	@InjectMocks
	private LoanService loanService;

	@Test
	public void testCalculateMonthlyPayment() {

		Money expected = Money.of(CURRENCY, 1520.06D);
		assertEquals(expected, loanService.calculateMonthlyPayment(4.5F, 30D, new BigDecimal(300000), CURRENCY));

	}

	@Test
	public void testCalcualteAnnualDebtService() {
		Money expected = Money.of(CURRENCY, (1520.06D * 12));
		assertEquals(expected, loanService.calculateAnnualDebtService(4.5F, 30D, new BigDecimal(300000), CURRENCY));
	}
}
