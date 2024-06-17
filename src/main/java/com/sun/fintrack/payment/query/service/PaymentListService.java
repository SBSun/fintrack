package com.sun.fintrack.payment.query.service;

import com.sun.fintrack.payment.query.dao.PaymentListDao;
import com.sun.fintrack.payment.query.repository.PaymentCategoryRepository;
import com.sun.fintrack.payment.request.PaymentStatsRequest;
import com.sun.fintrack.payment.response.PaymentCategoryListResponse;
import com.sun.fintrack.payment.response.PaymentListResponse;
import com.sun.fintrack.payment.response.PaymentStatsResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${cloud.aws.s3.url}")
  private String s3ImageUrl;

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
   * 일일 결제 내역 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<PaymentListResponse> getDailyList(String date) {
    List<PaymentListResponse> list = paymentListDao.getDailyList(date);
    setImageUrl(list);

    return list;
  }

  /**
   * 결제 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public PaymentStatsResponse getList(PaymentStatsRequest param) {
    return paymentListDao.getList(param);
  }

  /**
   * 월별 결제 내역 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<PaymentListResponse> getMonthlyList(Integer year, Integer month) {
    List<PaymentListResponse> list = paymentListDao.getMonthlyList(year, month);
    setImageUrl(list);

    return list;
  }

  /**
   * 결제 내용 검색 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<PaymentListResponse> getSearchList(String keyword) {
    List<PaymentListResponse> list = paymentListDao.getSearchList(keyword);
    setImageUrl(list);

    return list;
  }

  private void setImageUrl(List<PaymentListResponse> list) {
    list.forEach(it -> {
      if (!StringUtils.isBlank(it.getImage())) {
        it.setImage(s3ImageUrl + it.getImage());
      }
    });
  }
}
