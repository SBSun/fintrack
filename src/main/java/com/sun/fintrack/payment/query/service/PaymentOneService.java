package com.sun.fintrack.payment.query.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.payment.domain.Payment;
import com.sun.fintrack.payment.domain.PaymentCategory;
import com.sun.fintrack.payment.query.repository.PaymentCategoryRepository;
import com.sun.fintrack.payment.query.repository.PaymentRepository;
import com.sun.fintrack.payment.response.PaymentDetailResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 결제 정보 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class PaymentOneService {

  private final PaymentRepository paymentRepository;
  private final PaymentCategoryRepository paymentCategoryRepository;

  /**
   * 결제 카테고리 엔티티 단일 조회
   *
   * @param categoryId 카테고리 ID
   * @return 결제 엔티티
   */
  @Transactional(readOnly = true)
  public PaymentCategory getCategory(Long categoryId) {
    return paymentCategoryRepository.findById(categoryId)
                                    .orElseThrow(() -> new ValidationException("payment.category.not_found"));
  }

  /**
   * 결제 단일 상세 조회
   *
   * @param paymentSeq 결제 일련번호
   * @return 결제 엔티티
   */
  @Transactional(readOnly = true)
  public PaymentDetailResponse getDetail(Long paymentSeq) {
    return new PaymentDetailResponse(getOne(paymentSeq));
  }

  /**
   * 결제 엔티티 단일 조회
   *
   * @param paymentSeq 결제 일련번호
   * @return 결제 엔티티
   */
  @Transactional(readOnly = true)
  public Payment getOne(Long paymentSeq) {
    return paymentRepository.findById(paymentSeq).orElseThrow(() -> new ValidationException("payment.not_found"));
  }
}
