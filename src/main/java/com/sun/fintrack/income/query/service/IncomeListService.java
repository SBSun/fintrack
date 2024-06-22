package com.sun.fintrack.income.query.service;

import com.sun.fintrack.income.query.dao.IncomeListDao;
import com.sun.fintrack.income.request.IncomeStatsRequest;
import com.sun.fintrack.income.response.IncomeListResponse;
import com.sun.fintrack.income.response.IncomeStatsResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 소득 목록 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class IncomeListService {

  private final IncomeListDao incomeListDao;

  /**
   * 일일 소득 내역 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<IncomeListResponse> getDailyList(String date) {
    return incomeListDao.selectDailyList(date);
  }

  /**
   * 소득 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public IncomeStatsResponse getList(IncomeStatsRequest param) {
    return incomeListDao.select(param);
  }

  /**
   * 월별 소득 내역 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<IncomeListResponse> getMonthlyList(Integer year, Integer month) {
    return incomeListDao.selectMonthlyList(year, month);
  }

  /**
   * 소득 내용 검색 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<IncomeListResponse> getSearchList(String keyword) {
    return incomeListDao.selectSearchList(keyword);
  }
}
