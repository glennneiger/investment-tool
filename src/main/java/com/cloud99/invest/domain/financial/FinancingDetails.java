package com.cloud99.invest.domain.financial;

import com.cloud99.invest.validation.groups.AmortizingGroup;
import com.cloud99.invest.validation.groups.InterestOnlyGroup;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;

@Document
public class FinancingDetails {

	public enum LoanType {
		AMORTIZING, INTEREST_ONLY, CASH
	}

	// Percent of the total loan amount
	@Getter
	@Setter
	private Double loanOriginationPoints = 0D;

	@Getter
	@Setter
	@NotNull(message = "loan.type.required")
	private LoanType loanType = LoanType.AMORTIZING;

	@Getter
	@Setter
	@NotNull(message = "loan.amount.required")
	private BigDecimal loanAmount = new BigDecimal(0, new MathContext(2, RoundingMode.HALF_EVEN));

	@Getter
	@Setter
	private BigDecimal downPayment = new BigDecimal(0, new MathContext(2, RoundingMode.HALF_EVEN));

	@Getter
	@Setter
	@NotNull(message = "interest.rate.required", groups = AmortizingGroup.class)
	private Float interestRate = 0F;

	@Getter
	@Setter
	@NotNull(message = "loan.term.required", groups = { AmortizingGroup.class, InterestOnlyGroup.class })
	private Double loanTermYears = 30D;

	@Transient
	public Money getTotalUpfrontMoney(CurrencyUnit currency) {
		Money total = Money.of(currency, 0);
		if (getLoanOriginationPoints() != null && getLoanOriginationPoints() > 0) {
			BigDecimal loanPoints = getLoanAmount().multiply(BigDecimal.valueOf(getLoanOriginationPoints()));
			total = total.plus(loanPoints);
		}
		total = total.plus(getDownPayment());
		return total;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
