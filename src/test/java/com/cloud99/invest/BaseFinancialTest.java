package com.cloud99.invest;

import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.financial.FinancingDetails;
import com.cloud99.invest.domain.financial.FinancingDetails.LoanType;
import com.cloud99.invest.domain.financial.rental.RentalExpences;
import com.cloud99.invest.domain.financial.rental.RentalIncome;
import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;
import com.cloud99.invest.domain.financial.rental.ReoccuringExpense;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

public abstract class BaseFinancialTest extends BaseMockitoTest {

	public FlipPropertyFinances buildFlipPropertyFinances(
			double arv, 
			double desiredProfit, 
			Collection<ItemizedCost> holdingCosts, 
			int holdingDays, 
			PurchaseDetails purchaseDetails, 
			Collection<ItemizedCost> saleClosingCosts) {
		
		FlipPropertyFinances f = new FlipPropertyFinances();
		f.setAfterRepairValue(BigDecimal.valueOf(arv));
		f.setDesiredProfit(BigDecimal.valueOf(desiredProfit));
		f.setHoldingCosts(holdingCosts);
		f.setHoldingDays(holdingDays);
		f.setPurchaseDetails(purchaseDetails);
		f.setSaleClosingCosts(saleClosingCosts);
		return f;
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

	public Collection<ItemizedCost> buildItemizedCost(double cost) {
		return Arrays.asList(new ItemizedCost("Cost", cost));
	}

	public Collection<ItemizedCost> buildItemizedCost(double cost1, double cost2) {
		return Arrays.asList(new ItemizedCost("Cost1", cost1), new ItemizedCost("Cost2", cost2));
	}

	public Collection<ReoccuringExpense> buildReoccuringCost(double cost) {
		return Arrays.asList(new ReoccuringExpense("Expence1", new BigDecimal(cost), Frequency.MONTHY));
	}

	public PurchaseDetails buildPurchaseDetails(double purchasePrice, double arv) {

		PurchaseDetails d = new PurchaseDetails();
		d.setAfterRepairValue(new BigDecimal(arv));
		d.setPurchasePrice(new BigDecimal(purchasePrice));

		return d;
	}

	public RentalIncome buildMonthlyIncome(double deposit, double rent, double otherIncome) {

		RentalIncome i = new RentalIncome();
		i.setDeposit(new BigDecimal(deposit));
		i.setGrossRent(new BigDecimal(rent));
		i.setOtherIncome(new BigDecimal(otherIncome));
		i.setRentUnit(Frequency.MONTHY);

		return i;
	}

	public Collection<ReoccuringExpense> buildMonthlyOperatingExpences(int cost1, int cost2, int cost3) {
		return Arrays.asList(
				new ReoccuringExpense("Cost1", new BigDecimal(cost1), Frequency.MONTHY), new ReoccuringExpense("Cost2", new BigDecimal(cost2), Frequency.MONTHY), new ReoccuringExpense("Cost3", new BigDecimal(cost3), Frequency.MONTHY));
	}

	public Collection<ReoccuringExpense> buildMonthlyExpenses(double expense1, double expense2) {
		return Arrays.asList(
				new ReoccuringExpense("Expence1", new BigDecimal(expense1), Frequency.MONTHY), new ReoccuringExpense("Expence2", new BigDecimal(expense2), Frequency.MONTHY));
	}
}
