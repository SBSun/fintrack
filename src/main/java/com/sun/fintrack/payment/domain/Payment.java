package com.sun.fintrack.payment.domain;

import com.sun.fintrack.common.domain.BaseTimeEntity;
import com.sun.fintrack.member.domain.Member;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "PAYMENT")
public class Payment extends BaseTimeEntity {

  /**
   * 결제 일련번호
   */
  @Id
  @Column(name = "PM_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentSeq;
  /**
   * 결제 내용
   */
  @Column(name = "PM_CTT", nullable = false)
  private String content;
  /**
   * 결제 금액
   */
  @Column(name = "PM_PRC", nullable = false)
  private Long price;
  /**
   * 결제일시
   */
  @Column(name = "PM_DT", nullable = false)
  private LocalDateTime paymentDt;
  /**
   * 회원
   */
  @ManyToOne
  @JoinColumn(name = "MB_SEQ", nullable = false)
  private Member member;
  /**
   * 카테고리
   */
  @ManyToOne
  @JoinColumn(name = "PM_CTG_ID", nullable = false)
  private PaymentCategory category;

  public Payment(String content, Long price, LocalDateTime paymentDt, Member member, PaymentCategory category) {
    this.content = content;
    this.price = price;
    this.paymentDt = paymentDt;
    this.member = member;
    this.category = category;
  }

  /**
   * 결제 정보 수정
   *
   * @param content  결제 내용
   * @param price    결제 금액
   * @param category 카테고리
   */
  public void modify(String content, Long price, LocalDateTime paymentDt, PaymentCategory category) {
    this.content = content;
    this.price = price;
    this.paymentDt = paymentDt;
    this.category = category;
  }
}
