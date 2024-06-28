package com.sun.fintrack.history.query.service;

import com.sun.fintrack.history.query.dao.HistoryListDao;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;
import com.sun.fintrack.history.response.HistoryResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 내역 정보 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class HistoryOneService {

  private final HistoryListDao historyListDao;

  /**
   * 일일 내역 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public HistoryResponse getOne(String type, String date, Long memberSeq) {
    return new HistoryResponse(historyListDao.selectDailyList(type, date, memberSeq));
  }

  /**
   * 월별 내역 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public HistoryResponse getOne(HistoryMonthlyRequest param) {

    return new HistoryResponse(historyListDao.selectMonthlyList(param));
  }

  /**
   * 내용 검색 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public HistoryResponse getOne(String keyword) {

    return new HistoryResponse(historyListDao.selectSearchList(keyword));
  }
}
