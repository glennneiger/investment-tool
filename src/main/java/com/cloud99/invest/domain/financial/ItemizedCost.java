package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.MongoDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This domain object represents a generic, one time, cost or expense
 */
@NoArgsConstructor
public class ItemizedCost implements MongoDocument {
	private static final long serialVersionUID = 5407793684155600984L;

	@JsonIgnore
	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name;

	@Setter
	private BigDecimal cost = BigDecimal.valueOf(0);

	public ItemizedCost(String name, double cost) {
		this(name, BigDecimal.valueOf(cost));
	}

	public ItemizedCost(String name, BigDecimal cost) {
		super();
		this.name = name;
		this.cost = cost;
	}

	public BigDecimal getCost() {
		return cost.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public String toString() {
		return "ItemizedCost= name:" + getName() + ", cost:" + getCost();
	}
}
