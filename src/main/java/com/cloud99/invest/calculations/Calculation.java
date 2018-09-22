package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.account.SubscriptionType;
import com.cloud99.invest.domain.financial.PropertyFinances;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public interface Calculation<RETURN_TYPE> {

	public enum CalculationType {

		CASH_ON_CASH(CashOnCash.class, SubscriptionType.PAID), 
		NOI(NetOperatingIncome.class, SubscriptionType.FREE), 
		ANNUAL_DEBT_SERVICE(AnnualDebtService.class, SubscriptionType.FREE), 
		MONTHLY_PAYMENT(MonthlyPayment.class, SubscriptionType.FREE),
		DEBT_SERVICE_RATIO(DebtServiceRatio.class, SubscriptionType.FREE),
		CAP_RATE(CapRate.class, SubscriptionType.FREE), 
		RENT_COST(RentCost.class, SubscriptionType.FREE), 
		GROSS_YIELD(GrossYield.class, SubscriptionType.FREE),
		MAXIMUM_ALLOWABLE_OFFER_AMOUNT(MaximumAllowableOfferAmount.class, SubscriptionType.PAID);
		
		@Getter
		Class<? extends Calculation<?>> calcClass;
		
		@Getter
		SubscriptionType subscriptionType;
		
		private CalculationType(Class<? extends Calculation<?>> calcClass, SubscriptionType subscriptionType) {
			this.calcClass = calcClass;
			this.subscriptionType = subscriptionType;
		}
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
