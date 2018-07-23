package com.cloud99.invest.services.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

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
		DEBT_SERVICE_RATIO(DebtServiceRatio.class);

		@Getter
		private Class<? extends Calculation<?>> calcClass;

	}

	public RETURN_TYPE calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations);

}
