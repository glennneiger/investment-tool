package com.cloud99.invest.domain.financial;

import com.cloud99.invest.converters.json.CurrencyUnitDeserializer;
import com.cloud99.invest.converters.json.CurrencyUnitSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.CurrencyUnit;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO - NG - add validation annotations to this class

@NoArgsConstructor
public class PropertyFinances implements Serializable {
	private static final long serialVersionUID = 261293800631034168L;

	@JsonSerialize(using = CurrencyUnitSerializer.class)
	@JsonDeserialize(using = CurrencyUnitDeserializer.class)
	@Getter
	@Setter
	private CurrencyUnit currency;

	@Getter
	@Setter
	private Expences expences;

	@Getter
	@Setter
	private Income income;

	@Getter
	@Setter
	private FinancialAssumptions assumptions;

	@Getter
	@Setter
	private PurchaseDetails purchaseDetails;

	public PropertyFinances(Expences expences, Income income, PurchaseDetails purchaseDetails, CurrencyUnit currency) {
		super();
		this.expences = expences;
		this.income = income;
		this.purchaseDetails = purchaseDetails;
		this.currency = currency;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
