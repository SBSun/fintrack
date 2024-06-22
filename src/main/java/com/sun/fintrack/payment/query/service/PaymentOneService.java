package com.sun.fintrack.payment.query.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.payment.domain.Payment;
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
