package com.cloud99.invest.domain;

public enum TimeUnit {

	DAILY(360), WEEKLY(52), MONTHY(12), ANNUALLY(1);

	private int annualPeriods;

	private TimeUnit(int annualPeriods) {
		this.annualPeriods = annualPeriods;
	}

	public int getAnnualPeriods() {
		return annualPeriods;
	}
}
