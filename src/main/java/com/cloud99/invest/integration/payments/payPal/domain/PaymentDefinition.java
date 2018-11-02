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
@JsonPropertyOrder({ "name", "type", "frequency", "frequency_interval", "amount", "cycles", "charge_models" })
public class PaymentDefinition implements Serializable {
	private final static long serialVersionUID = 7794233284982614768L;

	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private String type;
	@JsonProperty("frequency")
	private String frequency;
	@JsonProperty("frequency_interval")
	private String frequencyInterval;
	@JsonProperty("amount")
	@Valid
	private Amount amount;
	@JsonProperty("cycles")
	private String cycles;
	@JsonProperty("charge_models")
	@Valid
	private List<ChargeModel> chargeModels = null;
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

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("frequency")
	public String getFrequency() {
		return frequency;
	}

	@JsonProperty("frequency")
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@JsonProperty("frequency_interval")
	public String getFrequencyInterval() {
		return frequencyInterval;
	}

	@JsonProperty("frequency_interval")
	public void setFrequencyInterval(String frequencyInterval) {
		this.frequencyInterval = frequencyInterval;
	}

	@JsonProperty("amount")
	public Amount getAmount() {
		return amount;
	}

	@JsonProperty("amount")
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	@JsonProperty("cycles")
	public String getCycles() {
		return cycles;
	}

	@JsonProperty("cycles")
	public void setCycles(String cycles) {
		this.cycles = cycles;
	}

	@JsonProperty("charge_models")
	public List<ChargeModel> getChargeModels() {
		return chargeModels;
	}

	@JsonProperty("charge_models")
	public void setChargeModels(List<ChargeModel> chargeModels) {
		this.chargeModels = chargeModels;
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
