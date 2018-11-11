package com.cloud99.invest.calculations.flip;

import com.cloud99.invest.calculations.CalculationType;
import com.cloud99.invest.domain.account.MembershipType;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public enum FlipCalculationType implements CalculationType {

	MAXIMUM_ALLOWABLE_OFFER_AMOUNT_70_PERCENT(MaxAllowableOffer70Percent.class, MembershipType.PAID),
	MAXIMUM_ALLOWABLE_OFFER_AMOUNT_COST_BASED(MaxAllowableOfferCostBased.class, MembershipType.PAID), 
	TOTAL_PROJECT_COSTS(TotalProjectCosts.class, MembershipType.FREE);

	@Getter
	private Class<? extends FlipCalculation<?>> calcClass;

	@Getter
	private MembershipType membershipAccess;

	public static List<FlipCalculationType> ALL_CALCULATIONS = Arrays.asList(FlipCalculationType.values());

	private FlipCalculationType(Class<? extends FlipCalculation<?>> calcClass, MembershipType membershipAccess) {
		this.calcClass = calcClass;
		this.membershipAccess = membershipAccess;
	}

	@Override
	public List<FlipCalculationType> getAllCalculations() {
		return ALL_CALCULATIONS;
	}
}
