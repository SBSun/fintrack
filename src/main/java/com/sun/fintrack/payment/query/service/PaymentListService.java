package com.sun.fintrack.payment.query.service;

import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.payment.query.dao.PaymentListDao;
import com.sun.fintrack.payment.query.repository.PaymentCategoryRepository;
import com.sun.fintrack.payment.response.PaymentCategoryListResponse;
import com.sun.fintrack.payment.response.PaymentDailyListResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 결제 목록 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class PaymentListService {

  private final PaymentCategoryRepository paymentCategoryRepository;

  private final PaymentListDao paymentListDao;

  /**
   * 결제 카테고리 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<PaymentCategoryListResponse> getCategoryList() {
    return paymentCategoryRepository.findAll()
                                    .stream()
                                    .map(c -> new PaymentCategoryListResponse(c.getCategoryId(), c.getName()))
                                    .toList();
  }

  /**
   * 일일 결제 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<PaymentDailyListResponse> getDailyList() {
    return paymentListDao.getList(MemberUtils.getMemberSeq());
  }
}
