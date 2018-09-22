package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.MongoDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This domain object represents a generic cost or expense with an associated
 * frequency of the cost or expense
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

	@Getter
	@Setter
	private BigDecimal cost = new BigDecimal(0, new MathContext(2));

	public ItemizedCost(String name, double cost) {
		this(name, BigDecimal.valueOf(cost));
	}

	public ItemizedCost(String name, BigDecimal cost) {
		super();
		this.name = name;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
