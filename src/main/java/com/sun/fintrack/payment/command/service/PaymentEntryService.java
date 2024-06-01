package com.sun.fintrack.payment.command.service;

import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;
import com.sun.fintrack.payment.domain.Payment;
import com.sun.fintrack.payment.domain.PaymentCategory;
import com.sun.fintrack.payment.query.repository.PaymentCategoryRepository;
import com.sun.fintrack.payment.query.repository.PaymentRepository;
import com.sun.fintrack.payment.request.PaymentEntryRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 결제 정보 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class PaymentEntryService {

  private final PaymentRepository paymentRepository;
  private final PaymentCategoryRepository paymentCategoryRepository;

  private final MemberOneService memberOneService;


  /**
   * 결제 정보 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(PaymentEntryRequest param) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());
    PaymentCategory category = paymentCategoryRepository.getReferenceById(param.getCategoryId());

    paymentRepository.save(new Payment(param.getContent(), param.getPrice(), member, category));
  }
}
