package com.example.daraja.payment.DTO;

import java.io.Serializable;

public class C2BCallbackRequest implements Serializable {
    private String TransactionType;
    private String TransID;
    private String TransTime;
    private String TransAmount;
    private String BusinessShortCode;
    private String BillRefNumber;
    private String InvoiceNumber;
    private String OrgAccountBalance;
    private String ThirdPartyTransID;
    private String MSISDN;
    private String FirstName;
    private String MiddleName;
    private String LastName;

    // Getters and setters
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

    public String getTransAmount() {
        return TransAmount;
    }

    public void setTransAmount(String transAmount) {
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

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getOrgAccountBalance() {
        return OrgAccountBalance;
    }

    public void setOrgAccountBalance(String orgAccountBalance) {
        OrgAccountBalance = orgAccountBalance;
    }

    public String getThirdPartyTransID() {
        return ThirdPartyTransID;
    }

    public void setThirdPartyTransID(String thirdPartyTransID) {
        ThirdPartyTransID = thirdPartyTransID;
    }

    public String getPhoneNumber() {
        return MSISDN;
    }

    public void setPhoneNumber(String msisdn) {
        MSISDN = msisdn;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    @Override
    public String toString() {
        return "C2BCallbackRequest{" +
                "TransactionType='" + TransactionType + '\'' +
                ", TransID='" + TransID + '\'' +
                ", TransAmount='" + TransAmount + '\'' +
                ", BillRefNumber='" + BillRefNumber + '\'' +
                ", MSISDN='" + MSISDN + '\'' +
                '}';
    }
}
