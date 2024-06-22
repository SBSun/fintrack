package com.sun.fintrack.payment.command.service;

import com.sun.fintrack.payment.domain.Payment;
import com.sun.fintrack.payment.query.repository.PaymentRepository;
import com.sun.fintrack.payment.query.service.PaymentOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 결제 삭제 서비스
 */
@RequiredArgsConstructor
@Service
public class PaymentDeleteService {

  private final PaymentOneService paymentOneService;
  private final PaymentRepository paymentRepository;

  /**
   * 결제 삭제
   *
   * @param paymentSeq 결제 일련번호
   */
  @Transactional
  public void delete(Long paymentSeq) {
    Payment payment = paymentOneService.getOne(paymentSeq);
    paymentRepository.delete(payment);
  }
}
