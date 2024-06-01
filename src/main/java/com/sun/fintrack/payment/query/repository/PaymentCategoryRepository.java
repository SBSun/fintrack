package com.sun.fintrack.payment.query.repository;

import com.sun.fintrack.payment.domain.PaymentCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Long> {

}
