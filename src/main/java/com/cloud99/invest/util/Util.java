package com.cloud99.invest.util;

import com.cloud99.invest.domain.financial.ItemizedCost;

import org.apache.commons.lang3.StringUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

@Component
public class Util {

	public void validateNotNull(Object val, String msg) {
		if (val == null) {
			throw new IllegalStateException(msg);
		}
	}

	public void validateNotEmpty(String val, String msg) throws IllegalStateException {
		if (StringUtils.isEmpty(val)) {
			throw new IllegalStateException(msg);
		}
	}

	public String removeSpecialCharacters(String str) {
		return str.replaceAll("[^\\w\\s]", "");
	}

	public BigDecimal summerizeItemizedCosts(Collection<ItemizedCost> costs, int period) {

		BigDecimal total = BigDecimal.valueOf(0);
		for (ItemizedCost cost : costs) {
			total.add(cost.getCost().multiply(BigDecimal.valueOf(period)));
		}
		return total;
	}

	public Money summerizeItemizedCosts(CurrencyUnit currency, Collection<ItemizedCost> costs) {

		Money total = Money.of(currency, 0);
		for (ItemizedCost cost : costs) {
			total = total.plus(cost.getCost());
		}
		return total;
	}

	public Money convertToMoney(BigDecimal amount, String currency) {

		CurrencyUnit unit = null;
		if ("US".equalsIgnoreCase(currency)) {
			unit = CurrencyUnit.USD;
		} else {
			unit = CurrencyUnit.getInstance(currency);
			if (unit == null) {
				unit = CurrencyUnit.USD;
			}
		}
		if (amount == null) {
			return Money.of(unit, 0);
		}
		return Money.of(unit, amount);
	}

	public Money convertToMoney(Double amount, String currency) {
		return convertToMoney(BigDecimal.valueOf(amount), currency);
	}
}
