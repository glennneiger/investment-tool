package com.cloud99.invest.domain.financial;

import com.cloud99.invest.domain.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ItemizedCost implements Serializable {
	private static final long serialVersionUID = 5407793684155600984L;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private BigDecimal cost = new BigDecimal(0);

	@Getter
	@Setter
	private TimeUnit numberOfPeriodsAnnually;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
