package com.cloud99.invest.calculations.rental;

import com.cloud99.invest.calculations.CalculationType;
import com.cloud99.invest.domain.account.MembershipType;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public enum RentalCalculationType implements CalculationType {
	
	CASH_ON_CASH(CashOnCash.class, MembershipType.PAID), 
	NOI(NetOperatingIncome.class, MembershipType.FREE), 
	ANNUAL_DEBT_SERVICE(AnnualDebtService.class, MembershipType.FREE), 
	MONTHLY_PAYMENT(MonthlyDebtPayment.class, MembershipType.FREE),
	DEBT_SERVICE_RATIO(DebtServiceRatio.class, MembershipType.FREE),
	CAP_RATE(CapRate.class, MembershipType.FREE), 
	RENT_COST(RentCost.class, MembershipType.FREE), 
	GROSS_YIELD(GrossYield.class, MembershipType.FREE);

	@Getter
	private Class<? extends RentalCalculation<?>> calcClass;
	
	@Getter
	private MembershipType membershipAccess;

	public static List<RentalCalculationType> ALL_CALCULATIONS = Arrays.asList(RentalCalculationType.values());
	
	private RentalCalculationType(Class<? extends RentalCalculation<?>> calcClass, MembershipType membershipAccess) {
		this.calcClass = calcClass;
		this.membershipAccess = membershipAccess;
	}

	@Override
	public List<RentalCalculationType> getAllCalculations() {
		return ALL_CALCULATIONS;
	}
}
