package com.cloud99.invest.util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Component;

@Component
public class Util {

	public Money convertToMoney(String currency, Double amount) {
		return convertToMoney(amount, currency);
	}

	public Money convertToMoney(Double amount, String currency) {

		CurrencyUnit unit = null;
		if ("US".equalsIgnoreCase(currency)) {
			unit = CurrencyUnit.USD;
		} else {
			unit = CurrencyUnit.getInstance(currency);
			if (unit == null) {
				unit = CurrencyUnit.USD;
			}
		}
		return Money.of(unit, amount);
	}
}
