package com.sun.fintrack.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 결제 카테고리 엔티티
 */
@Entity
@Getter
@Table(name = "PAYMENT_CATEGORY")
public class PaymentCategory {

  /**
   * 결제 카테고리 ID
   */
  @Id
  @Column(name = "PM_CTG_ID", nullable = false)
  private Long categoryId;
  /**
   * 카테고리명
   */
  @Column(name = "PM_CTG_NM", nullable = false)
  private String name;
}
