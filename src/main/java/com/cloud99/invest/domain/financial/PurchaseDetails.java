package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Document
public class PurchaseDetails extends BaseDomainObject {
	private static final long serialVersionUID = -5837071805268532820L;

	@Getter
	@Setter
	private BigDecimal purchasePrice = new BigDecimal(0, new MathContext(2, RoundingMode.HALF_EVEN));

	@Getter
	@Setter
	private Collection<ItemizedCost> rehabCosts = new ArrayList<>(0);

	// Should not include any financing details like down payment, that should be
	// captured in the FinancingDetails object
	@NotNull(message = "closing.costs.required")
	@Getter
	@Setter
	private Collection<ItemizedCost> closingCosts = new ArrayList<>(0);

	@Getter
	@Setter
	private boolean isFinanced;

	@Getter
	@Setter
	private FinancingDetails financingDetails;

	@Transient
	public Money getTotalMoneyInDeal(CurrencyUnit currency) {

		Money total = Money.of(currency, 0);
		if (isFinanced) {
			total = total.plus(getTotalClosingCosts(currency));
			total = total.plus(financingDetails.getTotalUpfrontMoney(currency));
		} else {
			// cash purchase
			total = getTotalRehabCosts(currency).plus(purchasePrice);
		}
		return total;
	}

	@Transient
	public Money getTotalRehabCosts(CurrencyUnit currency) {

		return summerizeItemCosts(currency, rehabCosts);
	}

	@Transient
	public Money getTotalClosingCosts(CurrencyUnit currency) {

		return summerizeItemCosts(currency, closingCosts);
	}

	/**
	 * This will return the total amount of money out of pocket to purchase a property (including the loan and rehab costs)
	 * @param currency
	 * @return
	 */
	@Transient
	public Money getTotalPurchaseCost(CurrencyUnit currency) {

		Money total = Money.of(currency, 0);
		total = total
				.plus(purchasePrice)
				.plus(summerizeItemCosts(currency, closingCosts))
				.plus(getTotalRehabCosts(currency))
				.plus(getFinancingDetails().getDownPayment(), RoundingMode.HALF_EVEN);
		return total;
	}

	@Transient
	public Money summerizeItemCosts(CurrencyUnit currency, Collection<ItemizedCost> costs) {

		Money total = Money.of(currency, 0);
		for (ItemizedCost cost : costs) {
			total = total.plus(cost.getCost());
		}
		return total;
	}
}
