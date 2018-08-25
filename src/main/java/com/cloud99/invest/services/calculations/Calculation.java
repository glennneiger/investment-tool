package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Calculation<RETURN_TYPE> {

	@AllArgsConstructor
	public enum CalculationType {

		CASH_ON_CASH(CashOnCash.class), 
		NOI(NetOperatingIncome.class), 
		ANNUAL_DEBT_SERVICE(AnnualDebtService.class), 
		MONTHLY_PAYMENT(MonthlyPayment.class),
		DEBT_SERVICE_RATIO(DebtServiceRatio.class),
		CAP_RATE(CapRate.class), 
		RENT_COST(RentCost.class), 
		GROSS_YIELD(GrossYield.class);

		@Getter
		Class<? extends Calculation<?>> calcClass;

	}

	public static Map<CalculationType, Calculation<?>> ALL_CALCULATIONS = allCalculationsMap();

	public static Map<CalculationType, Calculation<?>> allCalculationsMap() {
		Map<CalculationType, Calculation<?>> map = new HashMap<>(CalculationType.values().length);

		for (CalculationType type : CalculationType.values()) {
			try {
				map.put(type, type.calcClass.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public RETURN_TYPE calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations);

}
