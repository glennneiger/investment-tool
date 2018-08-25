package com.cloud99.invest.util;

import com.cloud99.invest.domain.financial.ItemizedCost;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Util {

	public Money convertToMoney(String currency, Double amount) {
		return convertToMoney(amount, currency);
	}

	public static String removeSpecialCharacters(String str) {
		return str.replaceAll("[^\\w\\s]", "");
	}

	public Money summerizeItemizedCosts(CurrencyUnit currency, Collection<ItemizedCost> costs) {

		Money total = Money.of(currency, 0);
		for (ItemizedCost cost : costs) {
			total = total.plus(cost.getCost());
		}
		return total;
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
