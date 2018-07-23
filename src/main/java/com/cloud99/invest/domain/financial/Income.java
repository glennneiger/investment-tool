package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.BaseDomainObject;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.TimeUnit;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class Income extends BaseDomainObject implements MongoDocument {
	private static final long serialVersionUID = 4657190579372392482L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Income.class);

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

		Money m = getAnnualRentalIncome(currency).plus(getAnnualOtherIncome(currency));
		LOGGER.debug(m + " - total annual rental income");
		return m;
	}

	@Transient
	public Money getAnnualRentalIncome(CurrencyUnit currency) {

		Money income = Money.of(currency, grossRent.multiply(new BigDecimal(rentUnit.getAnnualPeriods())));
		LOGGER.debug(income.getAmount() + " - annual rental income");
		return income;
	}

	@Transient
	public Money getAnnualOtherIncome(CurrencyUnit currency) {

		Money m = Money.of(currency, otherIncome.multiply(new BigDecimal(otherIncomeUnit.getAnnualPeriods())));
		LOGGER.debug(m.getAmount() + " - Other annual income: " + m);
		return m;
	}

	@Transient
	public Money getTotalOperatingIncome(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);
		Money income = total.plus(grossRent).plus(otherIncome);
		LOGGER.debug(income.getAmount() + " - total operating income");
		return income;
	}

}
