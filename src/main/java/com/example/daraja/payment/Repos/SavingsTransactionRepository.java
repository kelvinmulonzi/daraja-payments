package com.example.daraja.payment.Repos;

import com.example.daraja.payment.Entities.SavingsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SavingsTransactionRepository extends JpaRepository<SavingsTransaction, Long> {

    @Modifying
    @Query(value = "INSERT INTO m_savings_transactions (savings_account_id, amount, transaction_date, payment_detail_id, " +
            "transaction_type_enum, tenant_id, account_number) " +
            "VALUES ((SELECT id FROM m_savings_account WHERE account_no = :accountNumber), :amount, NOW(), :paymentDetailId, 1, " +
            "(SELECT tenant_id FROM m_savings_account WHERE account_no = :accountNumber), :accountNumber)",
            nativeQuery = true)
    void saveC2BTransaction(@Param("accountNumber") String accountNumber,
                            @Param("amount") BigDecimal amount,
                            @Param("paymentDetailId") Long paymentDetailId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM m_savings_account WHERE account_no = :accountNumber)", nativeQuery = true)
    boolean accountExists(@Param("accountNumber") String accountNumber);
}
