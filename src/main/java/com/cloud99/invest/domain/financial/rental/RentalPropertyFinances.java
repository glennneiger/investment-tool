package com.cloud99.invest.domain.financial.rental;

import com.cloud99.invest.converters.json.CurrencyUnitDeserializer;
import com.cloud99.invest.converters.json.CurrencyUnitSerializer;
import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.financial.PropertyFinances;
import com.cloud99.invest.domain.financial.PurchaseDetails;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.CurrencyUnit;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RentalPropertyFinances implements MongoDocument, PropertyFinances {
	private static final long serialVersionUID = 261293800631034168L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String propertyId;

	@Valid
	@Getter
	@Setter
	private RentalExpences expences;

	@Valid
	@Getter
	@Setter
	private RentalIncome income;

	@Valid
	@Getter
	@Setter
	private RentalAssumptions assumptions;

	@Valid
	@Getter
	@Setter
	private PurchaseDetails purchaseDetails;

	public RentalPropertyFinances(RentalExpences expences, RentalIncome income, PurchaseDetails purchaseDetails) {
		super();
		this.expences = expences;
		this.income = income;
		this.purchaseDetails = purchaseDetails;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
