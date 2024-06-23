package com.sun.fintrack.trade.query.service;

import com.sun.fintrack.trade.query.dao.TradeListDao;
import com.sun.fintrack.trade.request.TradeMonthlyRequest;
import com.sun.fintrack.trade.request.TradeStatsRequest;
import com.sun.fintrack.trade.response.TradeListResponse;
import com.sun.fintrack.trade.response.TradeStatsResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 거래 목록 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class TradeListService {

  @Value("${cloud.aws.s3.url}")
  private String s3ImageUrl;

  private final TradeListDao tradeListDao;

  /**
   * 일일 거래 내역 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<TradeListResponse> getDailyList(String type, String date) {
    List<TradeListResponse> list = tradeListDao.selectDailyList(type, date);
    setImageUrl(list);

    return list;
  }

  /**
   * 거래 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public TradeStatsResponse getList(TradeStatsRequest param) {
    return tradeListDao.select(param);
  }

  /**
   * 월별 거래 내역 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<TradeListResponse> getMonthlyList(TradeMonthlyRequest param) {
    List<TradeListResponse> list = tradeListDao.selectMonthlyList(param);
    setImageUrl(list);

    return list;
  }

  /**
   * 거래 내용 검색 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<TradeListResponse> getSearchList(String keyword) {
    List<TradeListResponse> list = tradeListDao.selectSearchList(keyword);
    setImageUrl(list);

    return list;
  }

  private void setImageUrl(List<TradeListResponse> list) {
    list.forEach(it -> {
      if (StringUtils.isNotBlank(it.getImage())) {
        it.setImage(s3ImageUrl + it.getImage());
      }
    });
  }
}
