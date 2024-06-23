package com.sun.fintrack.category.command.service;

import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.query.service.CategoryOneService;
import com.sun.fintrack.category.request.CategoryModifyRequest;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 수정 서비스
 */
@RequiredArgsConstructor
@Service
public class CategoryModifyService {

  private final CategoryOneService categoryOneService;

  /**
   * 카테고리 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(CategoryModifyRequest param) {
    Category category = categoryOneService.getOne(param.getCategorySeq(), TradeType.fromCode(param.getType()));

    category.modify(param.getName());
  }
}