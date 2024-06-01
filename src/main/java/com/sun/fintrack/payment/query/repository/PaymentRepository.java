package com.sun.fintrack.payment.query.repository;

import com.sun.fintrack.payment.domain.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
