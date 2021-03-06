package com.cloud99.invest.calculations.flip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.Money;

import java.math.BigDecimal;

public class MaximumAllowableOfferCostBasedTest extends BaseFlipCalculationsTest {

	@Override
	public FlipCalculationType getCalculationType() {
		return FlipCalculationType.MAXIMUM_ALLOWABLE_OFFER_AMOUNT_COST_BASED;
	}

	@Override
	public <T> void assertResult(T result) {
		assertEquals(56900.00D, ((Money) result).getAmount().doubleValue());

	}

	@Override
	public FlipPropertyFinances getFlipPropertyFinances() {

		PurchaseDetails purchase = dataCreator.buildPurchaseDetails();
		purchase.setRehabCosts(dataCreator.buildCosts(25000));
		purchase.setClosingCosts(dataCreator.buildCosts(13100));

		FlipPropertyFinances propertyFinances = buildFlipPropertyFinances(135000d, 25000, dataCreator.buildCosts(10000), 30, purchase, dataCreator.buildCosts(5000));
		return propertyFinances;
	}

}
