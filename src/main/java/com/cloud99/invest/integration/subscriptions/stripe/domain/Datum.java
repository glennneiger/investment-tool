
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
    "amount",
    "currency",
    "description",
    "discountable",
    "livemode",
    "metadata",
    "period",
    "plan",
    "proration",
    "quantity",
    "subscription",
    "subscription_item",
    "type"
})
public class Datum {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("description")
    private String description;
    @JsonProperty("discountable")
    private Boolean discountable;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("period")
    private Period period;
    @JsonProperty("plan")
    private Plan plan;
    @JsonProperty("proration")
    private Boolean proration;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("subscription")
    private String subscription;
    @JsonProperty("subscription_item")
    private String subscriptionItem;
    @JsonProperty("type")
    private String type;
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

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("discountable")
    public Boolean getDiscountable() {
        return discountable;
    }

    @JsonProperty("discountable")
    public void setDiscountable(Boolean discountable) {
        this.discountable = discountable;
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
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("period")
    public Period getPeriod() {
        return period;
    }

    @JsonProperty("period")
    public void setPeriod(Period period) {
        this.period = period;
    }

    @JsonProperty("plan")
    public Plan getPlan() {
        return plan;
    }

    @JsonProperty("plan")
    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @JsonProperty("proration")
    public Boolean getProration() {
        return proration;
    }

    @JsonProperty("proration")
    public void setProration(Boolean proration) {
        this.proration = proration;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("subscription")
    public String getSubscription() {
        return subscription;
    }

    @JsonProperty("subscription")
    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    @JsonProperty("subscription_item")
    public String getSubscriptionItem() {
        return subscriptionItem;
    }

    @JsonProperty("subscription_item")
    public void setSubscriptionItem(String subscriptionItem) {
        this.subscriptionItem = subscriptionItem;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
