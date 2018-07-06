package com.cloud99.invest.domain.financial;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Document
public class Expences {

	private Integer vacancyRate = 0;
	private Collection<ItemizedCost> operatingExpences = new ArrayList<>(0);

	public Money getTotalAnnualOperatingExpences(CurrencyUnit currency) {

		Money total = Money.of(currency, 0);

		if (operatingExpences != null) {

			for (ItemizedCost cost : operatingExpences) {
				BigDecimal annual = cost.getCost().multiply(new BigDecimal(cost.getNumberOfPeriodsAnnually().getAnnualPeriods()));
				total = total.plus(annual);
			}
		}
		return total;
	}

	public Integer getVacancyRate() {
		return vacancyRate;
	}

	public void setVacancyRate(Integer vacancyRate) {
		this.vacancyRate = vacancyRate;
	}

	public Collection<ItemizedCost> getOperatingExpences() {
		return operatingExpences;
	}

	public void setOperatingExpences(Collection<ItemizedCost> operatingExpences) {
		this.operatingExpences = operatingExpences;
	}

}
