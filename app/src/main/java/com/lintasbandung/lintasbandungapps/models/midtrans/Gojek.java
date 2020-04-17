package com.lintasbandung.lintasbandungapps.models.midtrans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gojek {
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("gross_amount")
    @Expose
    private String grossAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("signature_key")
    @Expose
    private String signatureKey;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("fraud_status")
    @Expose
    private String fraudStatus;
    @SerializedName("settlement_time")
    @Expose
    private String settlementTime;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;

    public Gojek() {
    }

    public Gojek(String transactionTime, String grossAmount, String currency, String orderId, String paymentType, String signatureKey, String statusCode, String transactionId, String transactionStatus, String fraudStatus, String settlementTime, String statusMessage, String merchantId) {
        this.transactionTime = transactionTime;
        this.grossAmount = grossAmount;
        this.currency = currency;
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.signatureKey = signatureKey;
        this.statusCode = statusCode;
        this.transactionId = transactionId;
        this.transactionStatus = transactionStatus;
        this.fraudStatus = fraudStatus;
        this.settlementTime = settlementTime;
        this.statusMessage = statusMessage;
        this.merchantId = merchantId;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    public void setFraudStatus(String fraudStatus) {
        this.fraudStatus = fraudStatus;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
