package com.sun.fintrack.income.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.query.service.CategoryOneService;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.income.domain.Income;
import com.sun.fintrack.income.query.service.IncomeOneService;
import com.sun.fintrack.income.request.IncomeModifyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 소득 수정 서비스
 */
@RequiredArgsConstructor
@Service
public class IncomeModifyService {

  private final IncomeOneService incomeOneService;
  private final CategoryOneService categoryOneService;

  private final AssetOneService assetOneService;

  /**
   * 소득 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(IncomeModifyRequest param) {
    Income income = incomeOneService.getOne(param.getIncomeSeq());
    Asset asset = assetOneService.getOne(param.getAssetSeq());
    Category category = income.getCategory();
    if (!category.getCategorySeq().equals(param.getCategorySeq())) {
      category = categoryOneService.getOne(param.getCategorySeq(), CategoryType.INCOME);
    }

    income.modify(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getIncomeDt()), asset,
        category);
  }
}