package com.example.daraja.payment.Entities;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "m_payment_detail")
public class PaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "tenant_id")
    private Long tenantId;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
