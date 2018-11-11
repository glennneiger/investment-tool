package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.BaseFinancialTest;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseFlipCalculationsTest extends BaseFinancialTest {

	@Test
	public void testCalculate() throws Exception {

		Map<FlipCalculationType, FlipCalculation<?>> map = buildMap();

		PurchaseDetails purchase = dataCreator.buildPurchaseDetails();
		purchase.setRehabCosts(dataCreator.buildCosts(25000));
		purchase.setClosingCosts(dataCreator.buildCosts(13100));

		FlipPropertyFinances propertyFinances = getFlipPropertyFinances();

		FlipCalculation<?> calculation = map.get(getCalculationType());
		assertResult(calculation.calculate(propertyFinances, map, CurrencyUnit.USD));
	}

	public abstract FlipPropertyFinances getFlipPropertyFinances();

	private Map<FlipCalculationType, FlipCalculation<?>> buildMap() throws Exception {
		Map<FlipCalculationType, FlipCalculation<?>> map = new HashMap<>();
		FlipCalculationType.ALL_CALCULATIONS.forEach(c -> {
			try {
				map.put(c, c.getCalcClass().newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return map;
	}

	public abstract FlipCalculationType getCalculationType();

	public abstract <T> void assertResult(T result);

}
