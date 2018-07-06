package com.cloud99.invest.domain.financial;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Document
public class PurchaseDetails {

	private BigDecimal purchasePrice = new BigDecimal(0);
	private BigDecimal afterRepairValue = new BigDecimal(0);
	private Collection<ItemizedCost> rehabCosts = new ArrayList<>(0);
	private Collection<ItemizedCost> itemizedClosingCosts = new ArrayList<>(0);

	private FinancingDetails financingDetails;

	public Money getTotalPurchaseCost(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);
		total = total.plus(purchasePrice).plus(sumItems(currency, itemizedClosingCosts));
		return total;
	}

	public Money sumItems(CurrencyUnit currency, Collection<ItemizedCost> costs) {

		Money total = Money.of(currency, 0);
		for (ItemizedCost cost : costs) {
			total = total.plus(cost.getCost());
		}
		return total;
	}

	public Collection<ItemizedCost> getRehabCosts() {
		return rehabCosts;
	}

	public void setRehabCosts(Collection<ItemizedCost> rehabCosts) {
		this.rehabCosts = rehabCosts;
	}

	public Collection<ItemizedCost> getItemizedClosingCosts() {
		return itemizedClosingCosts;
	}

	public void setItemizedClosingCosts(Collection<ItemizedCost> itemizedClosingCosts) {
		this.itemizedClosingCosts = itemizedClosingCosts;
	}

	public FinancingDetails getFinancingDetails() {
		return financingDetails;
	}

	public void setFinancingDetails(FinancingDetails financingDetails) {
		this.financingDetails = financingDetails;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getAfterRepairValue() {
		return afterRepairValue;
	}

	public void setAfterRepairValue(BigDecimal afterRepairValue) {
		this.afterRepairValue = afterRepairValue;
	}

}
