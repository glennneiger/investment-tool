package com.cloud99.invest.domain.financial.rental;

import com.cloud99.invest.domain.Frequency;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;

public class RentalIncome implements Serializable {
	private static final long serialVersionUID = 4657190579372392482L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalIncome.class);

	// One time, often refundable, deposit
	@Getter
	@Setter
	private BigDecimal deposit = new BigDecimal(0, new MathContext(2, RoundingMode.HALF_EVEN));

	// Rental amount, before taxes, for the specified rentUnit
	@NotNull(message = "gross.rent.required")
	@Getter
	@Setter
	private BigDecimal grossRent = new BigDecimal(0, new MathContext(2, RoundingMode.HALF_EVEN));

	// How often is rent collected
	@Getter
	@Setter
	private Frequency rentUnit = Frequency.MONTHY;

	// Other services like laundry rooms
	@Getter
	@Setter
	private BigDecimal otherIncome = new BigDecimal(0, new MathContext(2, RoundingMode.HALF_EVEN));

	@Getter
	@Setter
	private Frequency otherIncomeUnit = Frequency.MONTHY;

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

		Money income = Money.of(currency, grossRent.multiply(new BigDecimal(rentUnit.getAnnualPeriods(), new MathContext(2, RoundingMode.HALF_EVEN))));
		LOGGER.debug(income.getAmount() + " - annual rental income");
		return income;
	}

	@Transient
	public Money getAnnualOtherIncome(CurrencyUnit currency) {

		Money m = Money.of(currency, otherIncome.multiply(new BigDecimal(otherIncomeUnit.getAnnualPeriods(), new MathContext(2, RoundingMode.HALF_EVEN))));
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
