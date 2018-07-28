package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.BaseFinancialTest;
import com.cloud99.invest.domain.Frequency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class ExpencesTest extends BaseFinancialTest {

	@InjectMocks
	private Expences expences;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	public void testGetTotalOperatingExpences_noExpences() {
		expences.setOperatingExpences(null);
		assertEquals(buildMoney(0), expences.getTotalAnnualOperatingExpences(CURRENCY));
	}

	@Test
	public void testGetTotalOperatingExpences() {
		expences.setOperatingExpences(buildMonthlyOperatingExpences(150, 75, 25));
		int expencePeriods = Frequency.MONTHY.getAnnualPeriods();
		assertEquals(buildMoney((150 * expencePeriods) + (75 * expencePeriods) + (25 * expencePeriods)), expences.getTotalAnnualOperatingExpences(CURRENCY));
	}
}
