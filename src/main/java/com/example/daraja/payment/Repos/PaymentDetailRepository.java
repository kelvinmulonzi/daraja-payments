package com.example.daraja.payment.Repos;

import com.example.daraja.payment.Entities.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    // Standard JPA methods are available automatically
}

