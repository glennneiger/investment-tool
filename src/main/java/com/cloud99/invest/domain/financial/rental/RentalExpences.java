package com.cloud99.invest.domain.financial.rental;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Document
public class RentalExpences implements Serializable {
	private static final long serialVersionUID = -4305901713759899401L;

	@Getter
	@Setter
	private float vacancyRate = 0.0F;

	@Getter
	@Setter
	private Collection<ReoccuringExpense> operatingExpences = new ArrayList<>(0);

	@Transient
	public Money getTotalAnnualOperatingExpences(CurrencyUnit currency) {

		Money total = Money.of(currency, 0);

		if (operatingExpences != null) {

			for (ReoccuringExpense cost : operatingExpences) {
				BigDecimal annual = cost.getCost().multiply(BigDecimal.valueOf(cost.getNumberOfPeriodsAnnually().getAnnualPeriods())).setScale(2, RoundingMode.HALF_EVEN);
				total = total.plus(annual, RoundingMode.HALF_EVEN);
			}
		}

		log.debug(total.getAmount() + " - annual operating expences");
		return total;
	}

	@Override
	public String toString() {
		return "RentalExpences: vacancyRate=" + getVacancyRate() + "operatingExpences=" + operatingExpences;
	}
}
