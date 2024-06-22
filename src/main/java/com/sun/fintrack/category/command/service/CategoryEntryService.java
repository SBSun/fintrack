package com.sun.fintrack.category.command.service;

import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.query.dao.CategoryOneDao;
import com.sun.fintrack.category.query.repository.CategoryRepository;
import com.sun.fintrack.category.request.CategoryEntryRequest;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.service.MemberOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 등록 서비스
 */
@RequiredArgsConstructor
@Service
public class CategoryEntryService {

  private final CategoryRepository categoryRepository;
  private final CategoryOneDao categoryOneDao;

  private final MemberOneService memberOneService;

  /**
   * 카테고리 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(CategoryEntryRequest param) {
    Member member = memberOneService.getReferenceOne(MemberUtils.getMemberSeq());

    CategoryType type = CategoryType.fromCode(param.getType());
    int order = categoryOneDao.selectTopOrder(type) + 1;

    categoryRepository.save(new Category(param.getName(), order, type, member));
  }
}
