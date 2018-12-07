
package com.cloud99.invest.integration.subscriptions.stripe.domain;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "object",
    "active",
    "aggregate_usage",
    "amount",
    "billing_scheme",
    "created",
    "currency",
    "interval",
    "interval_count",
    "livemode",
    "metadata",
    "nickname",
    "product",
    "tiers",
    "tiers_mode",
    "transform_usage",
    "trial_period_days",
    "usage_type"
})
public class Plan {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("aggregate_usage")
    private Object aggregateUsage;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("billing_scheme")
    private String billingScheme;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("interval")
    private String interval;
    @JsonProperty("interval_count")
    private Integer intervalCount;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("metadata")
    private PlanMetadata_ metadata;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("product")
    private String product;
    @JsonProperty("tiers")
    private Object tiers;
    @JsonProperty("tiers_mode")
    private Object tiersMode;
    @JsonProperty("transform_usage")
    private Object transformUsage;
    @JsonProperty("trial_period_days")
    private Object trialPeriodDays;
    @JsonProperty("usage_type")
    private String usageType;
    @JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("object")
    public String getObject() {
        return object;
    }

    @JsonProperty("object")
    public void setObject(String object) {
        this.object = object;
    }

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonProperty("aggregate_usage")
    public Object getAggregateUsage() {
        return aggregateUsage;
    }

    @JsonProperty("aggregate_usage")
    public void setAggregateUsage(Object aggregateUsage) {
        this.aggregateUsage = aggregateUsage;
    }

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("billing_scheme")
    public String getBillingScheme() {
        return billingScheme;
    }

    @JsonProperty("billing_scheme")
    public void setBillingScheme(String billingScheme) {
        this.billingScheme = billingScheme;
    }

    @JsonProperty("created")
    public Integer getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Integer created) {
        this.created = created;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("interval")
    public void setInterval(String interval) {
        this.interval = interval;
    }

    @JsonProperty("interval_count")
    public Integer getIntervalCount() {
        return intervalCount;
    }

    @JsonProperty("interval_count")
    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    @JsonProperty("livemode")
    public Boolean getLivemode() {
        return livemode;
    }

    @JsonProperty("livemode")
    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    @JsonProperty("metadata")
    public PlanMetadata_ getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(PlanMetadata_ metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    @JsonProperty("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @JsonProperty("product")
    public String getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(String product) {
        this.product = product;
    }

    @JsonProperty("tiers")
    public Object getTiers() {
        return tiers;
    }

    @JsonProperty("tiers")
    public void setTiers(Object tiers) {
        this.tiers = tiers;
    }

    @JsonProperty("tiers_mode")
    public Object getTiersMode() {
        return tiersMode;
    }

    @JsonProperty("tiers_mode")
    public void setTiersMode(Object tiersMode) {
        this.tiersMode = tiersMode;
    }

    @JsonProperty("transform_usage")
    public Object getTransformUsage() {
        return transformUsage;
    }

    @JsonProperty("transform_usage")
    public void setTransformUsage(Object transformUsage) {
        this.transformUsage = transformUsage;
    }

    @JsonProperty("trial_period_days")
    public Object getTrialPeriodDays() {
        return trialPeriodDays;
    }

    @JsonProperty("trial_period_days")
    public void setTrialPeriodDays(Object trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    @JsonProperty("usage_type")
    public String getUsageType() {
        return usageType;
    }

    @JsonProperty("usage_type")
    public void setUsageType(String usageType) {
        this.usageType = usageType;
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
