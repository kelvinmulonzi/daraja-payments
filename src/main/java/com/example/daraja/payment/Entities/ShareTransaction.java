package com.example.daraja.payment.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "m_share_transactions")
public class ShareTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "share_account_id")
    private Long shareAccountId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "payment_detail_id")
    private Long paymentDetailId;

    @Column(name = "transaction_type_enum")
    private Integer transactionTypeEnum;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "account_number")
    private String accountNumber;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShareAccountId() {
        return shareAccountId;
    }

    public void setShareAccountId(Long shareAccountId) {
        this.shareAccountId = shareAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(Long paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public Integer getTransactionTypeEnum() {
        return transactionTypeEnum;
    }

    public void setTransactionTypeEnum(Integer transactionTypeEnum) {
        this.transactionTypeEnum = transactionTypeEnum;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}