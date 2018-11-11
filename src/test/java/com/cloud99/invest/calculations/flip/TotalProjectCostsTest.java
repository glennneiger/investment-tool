package com.cloud99.invest.calculations.flip;

import static org.junit.Assert.assertEquals;

import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.Money;

import java.math.BigDecimal;

/**
 * Project Costs = Purchase Price + Purchase Costs + Holding Costs + Rehab Costs
 * (with Contingency) + Sale Costs
 */
public class TotalProjectCostsTest extends BaseFlipCalculationsTest {

	@Override
	public FlipCalculationType getCalculationType() {
		return FlipCalculationType.TOTAL_PROJECT_COSTS;
	}

	@Override
	public <T> void assertResult(T result) {

		// purchase price = 135000
		// rehab costs = 25000
		// closing costs = 13100
		// holding costs = 10000
		// sales closing costs = 5000
		// total = 188100
		assertEquals(Money.of(CURRENCY, 188100), result);
	}

	@Override
	public FlipPropertyFinances getFlipPropertyFinances() {

		PurchaseDetails purchase = dataCreator.buildPurchaseDetails();
		purchase.setPurchasePrice(BigDecimal.valueOf(135000));
		purchase.setFinanced(true);
		purchase.setRehabCosts(dataCreator.buildCosts(25000));
		purchase.setClosingCosts(dataCreator.buildCosts(13100));

		FlipPropertyFinances propertyFinances = buildFlipPropertyFinances(135000d, 25000, dataCreator.buildCosts(10000), 30, purchase, dataCreator.buildCosts(5000));
		return propertyFinances;
	}

}
