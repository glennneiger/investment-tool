package com.cloud99.invest.domain.financial;

import com.cloud99.invest.services.validationGroups.AmortizingGroup;
import com.cloud99.invest.services.validationGroups.InterestOnlyGroup;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Document
public class FinancingDetails {

	public enum LoanType {
		AMORTIZING, INTEREST_ONLY, CASH
	}

	@Getter
	@Setter
	@NotNull(message = "loan.type.required")
	private LoanType loanType;

	@Getter
	@Setter
	@NotNull(message = "loan.amount.required")
	private BigDecimal loanAmount;

	@Getter
	@Setter
	private BigDecimal downPayment = new BigDecimal(0);

	@Getter
	@Setter
	@NotNull(message = "interest.rate.required", groups = AmortizingGroup.class)
	private Float interestRate;

	@Getter
	@Setter
	@NotNull(message = "loan.term.required", groups = { AmortizingGroup.class, InterestOnlyGroup.class })
	private Double loanTermYears;

	@Getter
	@Setter
	private BigDecimal mortgageInsuranceAmount;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
