package com.cloud99.invest.domain.financial.rental;

import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.ItemizedCost;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents any expense, like a bill, that is required to be paid
 * on a pre-defined frequency like monthly or annually
 */
public class ReoccuringExpense extends ItemizedCost {
	private static final long serialVersionUID = 5787525036056633518L;

	@Getter
	@Setter
	private Frequency numberOfPeriodsAnnually;

	public ReoccuringExpense(String name, BigDecimal cost, Frequency numberOfPeriodsAnnually) {
		super(name, cost);
		this.numberOfPeriodsAnnually = numberOfPeriodsAnnually;
	}

	@Override
	public String toString() {
		return super.toString() + ", numberOfPeriodsAnnually=" + getNumberOfPeriodsAnnually();
	}
}
