
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
    "amount_due",
    "amount_paid",
    "amount_remaining",
    "application_fee",
    "attempt_count",
    "attempted",
    "auto_advance",
    "billing",
    "billing_reason",
    "charge",
    "currency",
    "customer",
    "date",
    "default_source",
    "description",
    "discount",
    "due_date",
    "ending_balance",
    "finalized_at",
    "hosted_invoice_url",
    "invoice_pdf",
    "lines",
    "livemode",
    "metadata",
    "next_payment_attempt",
    "number",
    "paid",
    "payment_intent",
    "period_end",
    "period_start",
    "receipt_number",
    "starting_balance",
    "statement_descriptor",
    "status",
    "subscription",
    "subtotal",
    "tax",
    "tax_percent",
    "total",
    "webhooks_delivered_at"
})
public class Invoice {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("amount_due")
    private Integer amountDue;
    @JsonProperty("amount_paid")
    private Integer amountPaid;
    @JsonProperty("amount_remaining")
    private Integer amountRemaining;
    @JsonProperty("application_fee")
    private Object applicationFee;
    @JsonProperty("attempt_count")
    private Integer attemptCount;
    @JsonProperty("attempted")
    private Boolean attempted;
    @JsonProperty("auto_advance")
    private Boolean autoAdvance;
    @JsonProperty("billing")
    private String billing;
    @JsonProperty("billing_reason")
    private String billingReason;
    @JsonProperty("charge")
    private Object charge;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("customer")
    private String customer;
    @JsonProperty("date")
    private Integer date;
    @JsonProperty("default_source")
    private Object defaultSource;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("discount")
    private Object discount;
    @JsonProperty("due_date")
    private Object dueDate;
    @JsonProperty("ending_balance")
    private Object endingBalance;
    @JsonProperty("finalized_at")
    private Object finalizedAt;
    @JsonProperty("hosted_invoice_url")
    private Object hostedInvoiceUrl;
    @JsonProperty("invoice_pdf")
    private Object invoicePdf;
    @JsonProperty("lines")
    private Lines lines;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("metadata")
    private Metadata__ metadata;
    @JsonProperty("next_payment_attempt")
    private Integer nextPaymentAttempt;
    @JsonProperty("number")
    private String number;
    @JsonProperty("paid")
    private Boolean paid;
    @JsonProperty("payment_intent")
    private Object paymentIntent;
    @JsonProperty("period_end")
    private Integer periodEnd;
    @JsonProperty("period_start")
    private Integer periodStart;
    @JsonProperty("receipt_number")
    private Object receiptNumber;
    @JsonProperty("starting_balance")
    private Integer startingBalance;
    @JsonProperty("statement_descriptor")
    private Object statementDescriptor;
    @JsonProperty("status")
    private String status;
    @JsonProperty("subscription")
    private Object subscription;
    @JsonProperty("subtotal")
    private Integer subtotal;
    @JsonProperty("tax")
    private Integer tax;
    @JsonProperty("tax_percent")
    private Object taxPercent;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("webhooks_delivered_at")
    private Object webhooksDeliveredAt;
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

    @JsonProperty("amount_due")
    public Integer getAmountDue() {
        return amountDue;
    }

    @JsonProperty("amount_due")
    public void setAmountDue(Integer amountDue) {
        this.amountDue = amountDue;
    }

    @JsonProperty("amount_paid")
    public Integer getAmountPaid() {
        return amountPaid;
    }

    @JsonProperty("amount_paid")
    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    @JsonProperty("amount_remaining")
    public Integer getAmountRemaining() {
        return amountRemaining;
    }

    @JsonProperty("amount_remaining")
    public void setAmountRemaining(Integer amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    @JsonProperty("application_fee")
    public Object getApplicationFee() {
        return applicationFee;
    }

    @JsonProperty("application_fee")
    public void setApplicationFee(Object applicationFee) {
        this.applicationFee = applicationFee;
    }

    @JsonProperty("attempt_count")
    public Integer getAttemptCount() {
        return attemptCount;
    }

    @JsonProperty("attempt_count")
    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    @JsonProperty("attempted")
    public Boolean getAttempted() {
        return attempted;
    }

    @JsonProperty("attempted")
    public void setAttempted(Boolean attempted) {
        this.attempted = attempted;
    }

    @JsonProperty("auto_advance")
    public Boolean getAutoAdvance() {
        return autoAdvance;
    }

    @JsonProperty("auto_advance")
    public void setAutoAdvance(Boolean autoAdvance) {
        this.autoAdvance = autoAdvance;
    }

    @JsonProperty("billing")
    public String getBilling() {
        return billing;
    }

    @JsonProperty("billing")
    public void setBilling(String billing) {
        this.billing = billing;
    }

    @JsonProperty("billing_reason")
    public String getBillingReason() {
        return billingReason;
    }

    @JsonProperty("billing_reason")
    public void setBillingReason(String billingReason) {
        this.billingReason = billingReason;
    }

    @JsonProperty("charge")
    public Object getCharge() {
        return charge;
    }

    @JsonProperty("charge")
    public void setCharge(Object charge) {
        this.charge = charge;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("customer")
    public String getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @JsonProperty("date")
    public Integer getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(Integer date) {
        this.date = date;
    }

    @JsonProperty("default_source")
    public Object getDefaultSource() {
        return defaultSource;
    }

    @JsonProperty("default_source")
    public void setDefaultSource(Object defaultSource) {
        this.defaultSource = defaultSource;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("discount")
    public Object getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    @JsonProperty("due_date")
    public Object getDueDate() {
        return dueDate;
    }

    @JsonProperty("due_date")
    public void setDueDate(Object dueDate) {
        this.dueDate = dueDate;
    }

    @JsonProperty("ending_balance")
    public Object getEndingBalance() {
        return endingBalance;
    }

    @JsonProperty("ending_balance")
    public void setEndingBalance(Object endingBalance) {
        this.endingBalance = endingBalance;
    }

    @JsonProperty("finalized_at")
    public Object getFinalizedAt() {
        return finalizedAt;
    }

    @JsonProperty("finalized_at")
    public void setFinalizedAt(Object finalizedAt) {
        this.finalizedAt = finalizedAt;
    }

    @JsonProperty("hosted_invoice_url")
    public Object getHostedInvoiceUrl() {
        return hostedInvoiceUrl;
    }

    @JsonProperty("hosted_invoice_url")
    public void setHostedInvoiceUrl(Object hostedInvoiceUrl) {
        this.hostedInvoiceUrl = hostedInvoiceUrl;
    }

    @JsonProperty("invoice_pdf")
    public Object getInvoicePdf() {
        return invoicePdf;
    }

    @JsonProperty("invoice_pdf")
    public void setInvoicePdf(Object invoicePdf) {
        this.invoicePdf = invoicePdf;
    }

    @JsonProperty("lines")
    public Lines getLines() {
        return lines;
    }

    @JsonProperty("lines")
    public void setLines(Lines lines) {
        this.lines = lines;
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
    public Metadata__ getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata__ metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("next_payment_attempt")
    public Integer getNextPaymentAttempt() {
        return nextPaymentAttempt;
    }

    @JsonProperty("next_payment_attempt")
    public void setNextPaymentAttempt(Integer nextPaymentAttempt) {
        this.nextPaymentAttempt = nextPaymentAttempt;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("paid")
    public Boolean getPaid() {
        return paid;
    }

    @JsonProperty("paid")
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @JsonProperty("payment_intent")
    public Object getPaymentIntent() {
        return paymentIntent;
    }

    @JsonProperty("payment_intent")
    public void setPaymentIntent(Object paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    @JsonProperty("period_end")
    public Integer getPeriodEnd() {
        return periodEnd;
    }

    @JsonProperty("period_end")
    public void setPeriodEnd(Integer periodEnd) {
        this.periodEnd = periodEnd;
    }

    @JsonProperty("period_start")
    public Integer getPeriodStart() {
        return periodStart;
    }

    @JsonProperty("period_start")
    public void setPeriodStart(Integer periodStart) {
        this.periodStart = periodStart;
    }

    @JsonProperty("receipt_number")
    public Object getReceiptNumber() {
        return receiptNumber;
    }

    @JsonProperty("receipt_number")
    public void setReceiptNumber(Object receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @JsonProperty("starting_balance")
    public Integer getStartingBalance() {
        return startingBalance;
    }

    @JsonProperty("starting_balance")
    public void setStartingBalance(Integer startingBalance) {
        this.startingBalance = startingBalance;
    }

    @JsonProperty("statement_descriptor")
    public Object getStatementDescriptor() {
        return statementDescriptor;
    }

    @JsonProperty("statement_descriptor")
    public void setStatementDescriptor(Object statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("subscription")
    public Object getSubscription() {
        return subscription;
    }

    @JsonProperty("subscription")
    public void setSubscription(Object subscription) {
        this.subscription = subscription;
    }

    @JsonProperty("subtotal")
    public Integer getSubtotal() {
        return subtotal;
    }

    @JsonProperty("subtotal")
    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    @JsonProperty("tax")
    public Integer getTax() {
        return tax;
    }

    @JsonProperty("tax")
    public void setTax(Integer tax) {
        this.tax = tax;
    }

    @JsonProperty("tax_percent")
    public Object getTaxPercent() {
        return taxPercent;
    }

    @JsonProperty("tax_percent")
    public void setTaxPercent(Object taxPercent) {
        this.taxPercent = taxPercent;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("webhooks_delivered_at")
    public Object getWebhooksDeliveredAt() {
        return webhooksDeliveredAt;
    }

    @JsonProperty("webhooks_delivered_at")
    public void setWebhooksDeliveredAt(Object webhooksDeliveredAt) {
        this.webhooksDeliveredAt = webhooksDeliveredAt;
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
