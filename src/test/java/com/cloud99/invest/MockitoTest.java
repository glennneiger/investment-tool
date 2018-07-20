package com.cloud99.invest;

import com.cloud99.invest.domain.TimeUnit;
import com.cloud99.invest.domain.financial.Expences;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.FinancingDetails.LoanType;
import com.cloud99.invest.domain.financial.ItemizedCost;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
// @RunWith(JUnitPlatform.class)
public abstract class MockitoTest {

	public static final CurrencyUnit CURRENCY = CurrencyUnit.USD;

	public Money buildMoney(double amt) {
		return Money.of(CurrencyUnit.USD, amt);
	}

	public FinancingDetails buildFinancingDetails(double loanAmount, double downPayment, float interestRate) {
	
		FinancingDetails d = new FinancingDetails();
		d.setDownPayment(new BigDecimal(downPayment * loanAmount));
		d.setInterestRate(interestRate);
		d.setLoanAmount(new BigDecimal(loanAmount));
		d.setLoanTermYears(30d);
		d.setLoanType(LoanType.AMORTIZING);
		
		return d;
	}

	public Expences buildExpences(Float vacancyRate, double operatingExpence) {
	
		Expences e = new Expences();
		e.setOperatingExpences(buildItemizedCost(operatingExpence));
		e.setVacancyRate(vacancyRate);
	
		return e;
	}

	public Collection<ItemizedCost> buildItemizedCost(double operatingExpence) {
		return Arrays.asList(new ItemizedCost("Expence1", new BigDecimal(operatingExpence), TimeUnit.MONTHY));
	}
}
