package com.sun.fintrack.history.query.service;

import com.sun.fintrack.history.dto.HistoryExcelDto;
import com.sun.fintrack.history.query.dao.HistoryListDao;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 내역 정보 목록 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class HistoryListService {

  private final HistoryListDao historyListDao;

  /**
   * 월별 내역 조회 - 엑셀
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<HistoryExcelDto> getList(HistoryMonthlyRequest param) {
    return historyListDao.selectMonthlyList(param)
                         .stream()
                         .map(it -> new HistoryExcelDto(it.getContent(), it.getPrice(), it.getTradeDt(),
                             it.getAssetName(), it.getCategoryName()))
                         .toList();
  }
}
