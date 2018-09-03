package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.Frequency;
import com.cloud99.invest.domain.MongoDocument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.AllArgsConstructor;
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

	@Getter
	@Setter
	private Frequency numberOfPeriodsAnnually;

	public ItemizedCost(String name, BigDecimal cost, Frequency numberOfPeriodsAnnually) {
		super();
		this.name = name;
		this.cost = cost;
		this.numberOfPeriodsAnnually = numberOfPeriodsAnnually;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
