package com.cloud99.invest.integration.payments.payPal.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.Valid;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "description", "type", "payment_definitions", "merchant_preferences" })
public class PayPalPlan implements Serializable {
	private final static long serialVersionUID = -6286169606599544909L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("type")
	private String type;

	@JsonProperty("payment_definitions")
	@Valid
	private List<PaymentDefinition> paymentDefinitions = null;

	@JsonProperty("merchant_preferences")
	@Valid
	private MerchantPreferences merchantPreferences;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("payment_definitions")
	public List<PaymentDefinition> getPaymentDefinitions() {
		return paymentDefinitions;
	}

	@JsonProperty("payment_definitions")
	public void setPaymentDefinitions(List<PaymentDefinition> paymentDefinitions) {
		this.paymentDefinitions = paymentDefinitions;
	}

	@JsonProperty("merchant_preferences")
	public MerchantPreferences getMerchantPreferences() {
		return merchantPreferences;
	}

	@JsonProperty("merchant_preferences")
	public void setMerchantPreferences(MerchantPreferences merchantPreferences) {
		this.merchantPreferences = merchantPreferences;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
