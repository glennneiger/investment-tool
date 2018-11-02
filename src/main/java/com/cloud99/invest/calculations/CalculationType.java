package com.cloud99.invest.calculations;

import com.cloud99.invest.domain.account.MembershipType;

import java.util.List;

public interface CalculationType {

	Class<?> getCalcClass();

	MembershipType getMembershipAccess();
	
	List<? extends CalculationType> getAllCalculations();
}
