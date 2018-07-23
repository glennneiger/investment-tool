package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;
import com.cloud99.invest.domain.MongoDocument;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Document
public class PurchaseDetails extends BaseDomainObject implements MongoDocument {
	private static final long serialVersionUID = -5837071805268532820L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private BigDecimal purchasePrice = new BigDecimal(0);

	@Getter
	@Setter
	private BigDecimal afterRepairValue = new BigDecimal(0);

	@Getter
	@Setter
	private Collection<ItemizedCost> rehabCosts = new ArrayList<>(0);

	@Getter
	@Setter
	private Collection<ItemizedCost> itemizedClosingCosts = new ArrayList<>(0);

	@Getter
	@Setter
	private FinancingDetails financingDetails;

	@Transient
	public Money getTotalMoneyInDeal(CurrencyUnit currency) {

		return getTotalRehabCosts(currency).plus(financingDetails.getDownPayment());
	}

	@Transient
	public Money getTotalRehabCosts(CurrencyUnit currency) {

		return summerizeItemCosts(currency, rehabCosts);
	}

	@Transient
	public Money getTotalPurchaseCost(CurrencyUnit currency) {

		Money total = Money.of(currency, 0);
		total = total.plus(purchasePrice).plus(summerizeItemCosts(currency, itemizedClosingCosts));
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
