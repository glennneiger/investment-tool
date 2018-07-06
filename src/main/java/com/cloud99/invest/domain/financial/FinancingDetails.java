package com.cloud99.invest.domain.financial;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class FinancingDetails {

	public enum LoanType {
		AMORTIZING, INTEREST_ONLY, CASH
	}

	private LoanType loanType;
	private BigDecimal loanAmount;
	private BigDecimal downPayment;
	private Float interestRate;
	private Double loanTermYears;
	private BigDecimal mortgageInsuranceAmount;

	public BigDecimal getMortgageInsuranceAmount() {
		return mortgageInsuranceAmount;
	}

	public void setMortgageInsuranceAmount(BigDecimal mortgageInsuranceAmount) {
		this.mortgageInsuranceAmount = mortgageInsuranceAmount;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(BigDecimal downPayment) {
		this.downPayment = downPayment;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Double getLoanTermYears() {
		return loanTermYears;
	}

	public void setLoanTermYears(Double loanTermYears) {
		this.loanTermYears = loanTermYears;
	}
}
