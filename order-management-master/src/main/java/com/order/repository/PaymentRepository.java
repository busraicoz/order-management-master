package com.order.repository;

import com.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Payment Table Repository
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
