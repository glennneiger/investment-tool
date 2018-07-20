package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.TimeUnit;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class Income extends BaseDomainObject implements MongoDocument {
	private static final long serialVersionUID = 4657190579372392482L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private BigDecimal deposit = new BigDecimal(0);

	@Getter
	@Setter
	private BigDecimal grossRent = new BigDecimal(0);

	@Getter
	@Setter
	private TimeUnit rentUnit = TimeUnit.MONTHY;

	@Getter
	@Setter
	private BigDecimal otherIncome = new BigDecimal(0);

	@Getter
	@Setter
	private TimeUnit otherIncomeUnit = TimeUnit.MONTHY;

	@Transient
	public Money getTotalIncome(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);

		return total.plus(grossRent).plus(otherIncome).plus(deposit);
	}

	@Transient
	public Money getTotalAnnualOperatingIncome(CurrencyUnit currency) {

		return getAnnualRentalIncome(currency).plus(getAnnualOtherIncome(currency));
	}

	@Transient
	public Money getAnnualRentalIncome(CurrencyUnit currency) {

		return Money.of(currency, grossRent.multiply(new BigDecimal(rentUnit.getAnnualPeriods())));
	}

	@Transient
	public Money getAnnualOtherIncome(CurrencyUnit currency) {

		return Money.of(currency, otherIncome.multiply(new BigDecimal(otherIncomeUnit.getAnnualPeriods())));
	}

	@Transient
	public Money getTotalOperatingIncome(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);
		return total.plus(grossRent).plus(otherIncome);
	}

}
