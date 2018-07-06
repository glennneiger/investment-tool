package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class ItemizedCost {

	private String name;
	private BigDecimal cost = new BigDecimal(0);
	private TimeUnit numberOfPeriodsAnnually;

	public ItemizedCost() {
	}

	public ItemizedCost(String name, BigDecimal cost, TimeUnit numberOfPeriodsAnnually) {
		super();
		this.name = name;
		this.cost = cost;
		this.numberOfPeriodsAnnually = numberOfPeriodsAnnually;
	}

	public TimeUnit getNumberOfPeriodsAnnually() {
		return numberOfPeriodsAnnually;
	}

	public void setNumberOfPeriodsAnnually(TimeUnit numberOfPeriodsAnnually) {
		this.numberOfPeriodsAnnually = numberOfPeriodsAnnually;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
