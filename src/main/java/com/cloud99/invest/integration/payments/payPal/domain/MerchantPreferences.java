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
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "setup_fee", "return_url", "cancel_url", "auto_bill_amount", "initial_fail_amount_action", "max_fail_attempts" })
public class MerchantPreferences implements Serializable {
	private final static long serialVersionUID = -3357448789182332120L;

	@JsonProperty("setup_fee")
	@Valid
	private SetupFee setupFee;

	@JsonProperty("return_url")
	private String returnUrl;

	@JsonProperty("cancel_url")
	private String cancelUrl;

	@JsonProperty("auto_bill_amount")
	private String autoBillAmount;

	@JsonProperty("initial_fail_amount_action")
	private String initialFailAmountAction;

	@JsonProperty("max_fail_attempts")
	private String maxFailAttempts;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("setup_fee")
	public SetupFee getSetupFee() {
		return setupFee;
	}

	@JsonProperty("setup_fee")
	public void setSetupFee(SetupFee setupFee) {
		this.setupFee = setupFee;
	}

	@JsonProperty("return_url")
	public String getReturnUrl() {
		return returnUrl;
	}

	@JsonProperty("return_url")
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@JsonProperty("cancel_url")
	public String getCancelUrl() {
		return cancelUrl;
	}

	@JsonProperty("cancel_url")
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	@JsonProperty("auto_bill_amount")
	public String getAutoBillAmount() {
		return autoBillAmount;
	}

	@JsonProperty("auto_bill_amount")
	public void setAutoBillAmount(String autoBillAmount) {
		this.autoBillAmount = autoBillAmount;
	}

	@JsonProperty("initial_fail_amount_action")
	public String getInitialFailAmountAction() {
		return initialFailAmountAction;
	}

	@JsonProperty("initial_fail_amount_action")
	public void setInitialFailAmountAction(String initialFailAmountAction) {
		this.initialFailAmountAction = initialFailAmountAction;
	}

	@JsonProperty("max_fail_attempts")
	public String getMaxFailAttempts() {
		return maxFailAttempts;
	}

	@JsonProperty("max_fail_attempts")
	public void setMaxFailAttempts(String maxFailAttempts) {
		this.maxFailAttempts = maxFailAttempts;
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
