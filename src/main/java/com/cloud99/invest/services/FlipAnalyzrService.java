package com.cloud99.invest.services;

import com.cloud99.invest.calculations.flip.FlipCalculationFactory;
import com.cloud99.invest.calculations.flip.FlipCalculationType;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.AccountSettings;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;
import com.cloud99.invest.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * https://www.reikit.com/house-flipping-guide/fix-and-flip-deal-analysis#rate-of-return
 *
 */
@Slf4j
@Service
public class FlipAnalyzrService {

	@Autowired
	@Setter
	private Util util;

	@Autowired
	@Setter
	private AccountService accountService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private FlipCalculationFactory calcFactory;

	public Map<FlipCalculationType, Object> analyzeFlip(String accountId, FlipPropertyFinances flipFinances) {

		log.debug("CalculateFile with assumptions: " + flipFinances);


		AccountSettings acctSettings = accountService.getAccountSettings(accountId);
		User user = securityService.getCurrentSessionUser();
		Account acct = accountService.getAccountAndValidate(accountId);

		Map<FlipCalculationType, Object> flipResults = new HashMap<>();
		calcFactory.getAllCalculations().forEach((calcType, calcInstance) -> {
			if (user.getMembershipType().equals(calcType.getMembershipAccess())) {
				flipResults.put(calcType, calcInstance.calculate(flipFinances, calcFactory.getAllCalculations(), acctSettings.getCurrency()));
			}
		});
		return flipResults;
	}

	private void calculateForecast() {
		
		// 30 days

		// 60 days
		
		// 90 days
		
		// 180 days
		
	}


	/**
	 * The Rate of Return, is the ROI over a particular period of time. This number
	 * is useful to help you understand the effect of the deal on your overall
	 * business for that period of time. I typically measure it in terms of a year,
	 * and so the rate of return formula is: ROR = ROI/Holding Days * 365 For
	 * example say you have the following 2 oversimplified deals:
	 * 
	 * Deal 1: 90 day project, 20% ROI, Deal 2: 180 day project, 30% ROI
	 * 
	 * The ROR on the first deal is 81% (20/90*365), and the ROR on the second deal
	 * is 61% (30/180*365). That means that even though the ROI for the first deal
	 * is significantly lower than the second, your business will grow by an extra
	 * 20% for the year, if you choose to go after deals like the first one. The
	 * minimum ROR that we look for in our flip deals is 30%
	 *
	 */
	private void calculateRateOfReturn() {
		// TODO - NG - implement this calculation and see about making it a Calculation
		// class instead
	}


	// $20 per sq. ft for repair values

	// Purchase costs = .05% of purchase price

	// Selling costs = 5-6% (3% is typical)

	// Include 1% for other closing costs

}
