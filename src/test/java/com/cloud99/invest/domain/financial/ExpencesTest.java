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

public class ExpencesTest extends MockitoTest {

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
		expences.setOperatingExpences(buildOperatingExpences(150, 75, 25));
		int expencePeriods = TimeUnit.MONTHY.getAnnualPeriods();
		assertEquals(buildMoney((150 * expencePeriods) + (75 * expencePeriods) + (25 * expencePeriods)), expences.getTotalAnnualOperatingExpences(CURRENCY));
	}

	private Collection<ItemizedCost> buildOperatingExpences(int cost1, int cost2, int cost3) {
		return Arrays.asList(
				new ItemizedCost("Cost1", new BigDecimal(cost1), TimeUnit.MONTHY), 
				new ItemizedCost("Cost2", new BigDecimal(cost2), TimeUnit.MONTHY), 
				new ItemizedCost("Cost3", new BigDecimal(cost3), TimeUnit.MONTHY));
	}


}
