package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaximumAllowableOfferAmount implements Calculation<Money> {

	@Override
	public Money calculate(PropertyFinances propertyFinances, Map<CalculationType, Calculation<?>> allCalculations) {

		BigDecimal arv = propertyFinances.getPurchaseDetails().getAfterRepairValue();

		BigDecimal percent70 = arv.multiply(new BigDecimal(.7));

		Money cashInDeal = propertyFinances.getPurchaseDetails().getTotalMoneyInDeal(propertyFinances.getCurrency());
		log.debug("Total cash in the deal: " + cashInDeal);

		BigDecimal MAO = percent70.subtract(cashInDeal.getAmount()).setScale(2, RoundingMode.HALF_EVEN);
		log.debug("Maximum allowable offer: " + MAO);

		return Money.of(propertyFinances.getCurrency(), MAO);
	}

}
