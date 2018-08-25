package com.cloud99.invest;

import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.Expences;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.FinancingDetails.LoanType;
import com.cloud99.invest.domain.financial.Income;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.PurchaseDetails;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

public abstract class BaseFinancialTest extends BaseMockitoTest {

	public PropertyFinances buildPropertyFinances(double purchasePrice) {

		// 20% Down, 4.5% Interest rate
		FinancingDetails details = buildFinancingDetails(purchasePrice, 0.20D * purchasePrice, 4.5F);

		// rehab: $50k

		// annual operating expenses: $3000 - monthly: $20K - annually
		Expences expences = buildExpences(0F, 1666.666);

		// annual income: $45K - annually
		Income income = buildMonthlyIncome(0, 3750, 0);

		// After repair value (ARV) - 315000
		PurchaseDetails purchaseDetails = buildPurchaseDetails(purchasePrice, 315000D);
		purchaseDetails.setFinancingDetails(details);

		PropertyFinances cash = new PropertyFinances(expences, income, purchaseDetails, CURRENCY);

		return cash;
	}

	public static final CurrencyUnit CURRENCY = CurrencyUnit.USD;

	public Money buildMoney(double amt) {
		return Money.of(CurrencyUnit.USD, amt);
	}

	public FinancingDetails buildFinancingDetails(double loanAmount, double downPayment, float interestRate) {

		FinancingDetails d = new FinancingDetails();
		d.setDownPayment(new BigDecimal(downPayment));
		d.setInterestRate(interestRate);
		d.setLoanAmount(new BigDecimal(loanAmount));
		d.setLoanTermYears(30d);
		d.setLoanType(LoanType.AMORTIZING);

		return d;
	}

	public Expences buildExpences(float vacancyRate, double operatingExpence) {

		Expences e = new Expences();
		e.setOperatingExpences(buildItemizedCost(operatingExpence));
		e.setVacancyRate(vacancyRate);

		return e;
	}

	public Collection<ItemizedCost> buildItemizedCost(double Cost) {
		return Arrays.asList(new ItemizedCost("Expence1", new BigDecimal(Cost), Frequency.MONTHY));
	}

	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {

		PurchaseDetails d = new PurchaseDetails();
		d.setAfterRepairValue(new BigDecimal(arv));
		d.setPurchasePrice(new BigDecimal(purchasePrice));

		return d;
	}

	public Income buildMonthlyIncome(double deposit, double rent, double otherIncome) {

		Income i = new Income();
		i.setDeposit(new BigDecimal(deposit));
		i.setGrossRent(new BigDecimal(rent));
		i.setOtherIncome(new BigDecimal(otherIncome));
		i.setRentUnit(Frequency.MONTHY);

		return i;
	}

	public Collection<ItemizedCost> buildMonthlyOperatingExpences(int cost1, int cost2, int cost3) {
		return Arrays.asList(
				new ItemizedCost("Cost1", new BigDecimal(cost1), Frequency.MONTHY), 
				new ItemizedCost("Cost2", new BigDecimal(cost2), Frequency.MONTHY), 
				new ItemizedCost("Cost3", new BigDecimal(cost3), Frequency.MONTHY));
	}

	public Collection<ItemizedCost> buildMonthlyItemizedCost(double expense1, double expense2) {
		return Arrays.asList(
				new ItemizedCost("Expence1", new BigDecimal(expense1), Frequency.MONTHY),
				new ItemizedCost("Expence2", new BigDecimal(expense2), Frequency.MONTHY));
	}
}
