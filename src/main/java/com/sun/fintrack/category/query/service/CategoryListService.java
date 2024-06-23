package com.sun.fintrack.category.query.service;

import com.sun.fintrack.category.query.dao.CategoryListDao;
import com.sun.fintrack.category.response.CategoryListResponse;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 목록 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class CategoryListService {

  private final CategoryListDao categoryListDao;

  /**
   * 카테고리 목록 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public List<CategoryListResponse> getList(TradeType type) {
    return categoryListDao.select(type);
  }
}
