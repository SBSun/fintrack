package com.sun.fintrack.income.command.service;

import com.sun.fintrack.asset.domain.Asset;
import com.sun.fintrack.asset.query.service.AssetOneService;
import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.query.service.CategoryOneService;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.income.domain.Income;
import com.sun.fintrack.income.query.repository.IncomeRepository;
import com.sun.fintrack.income.request.IncomeEntryRequest;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 소득 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class IncomeEntryService {

  private final IncomeRepository incomeRepository;

  private final CategoryOneService categoryOneService;
  private final AssetOneService assetOneService;
  private final MemberOneService memberOneService;

  /**
   * 소득 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(IncomeEntryRequest param) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());
    Asset asset = assetOneService.getOne(param.getAssetSeq());
    Category category = categoryOneService.getOne(param.getCategorySeq(), CategoryType.INCOME);

    incomeRepository.save(
        new Income(param.getContent(), param.getPrice(), DateTimeUtils.convertToDateTime(param.getIncomeDt()), member,
            asset, category));
  }
}
