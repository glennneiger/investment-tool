package com.cloud99.invest.domain;

/**
 * Represents a frequency or period of time as it associates to a recurring
 * transaction. For each period, it also stores the number of periods in a year
 * to help calculate annual amounts.
 */
public enum Frequency {

	SINGLE(0), DAILY(365), WEEKLY(52), MONTHY(12), ANNUALLY(1);

	private int annualPeriods;

	private Frequency(int annualPeriods) {
		this.annualPeriods = annualPeriods;
	}

	public int getAnnualPeriods() {
		return annualPeriods;
	}

	@Override
	public String toString() {
		return "Frequency=" + name() + ", annualPeriods=" + getAnnualPeriods();
	}
}
