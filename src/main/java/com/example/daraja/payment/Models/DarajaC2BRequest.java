package com.example.daraja.payment.Models;

public class DarajaC2BRequest {
    private String TransactionType;
    private String TransID;
    private String TransTime;
    private Double TransAmount;
    private String BusinessShortCode;
    private String BillRefNumber;
    private String MSISDN;

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public String getTransTime() {
        return TransTime;
    }

    public void setTransTime(String transTime) {
        TransTime = transTime;
    }

    public Double getTransAmount() {
        return TransAmount;
    }

    public void setTransAmount(Double transAmount) {
        TransAmount = transAmount;
    }

    public String getBusinessShortCode() {
        return BusinessShortCode;
    }

    public void setBusinessShortCode(String businessShortCode) {
        BusinessShortCode = businessShortCode;
    }

    public String getBillRefNumber() {
        return BillRefNumber;
    }

    public void setBillRefNumber(String billRefNumber) {
        BillRefNumber = billRefNumber;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }
}