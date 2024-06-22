package com.sun.fintrack.category.query.service;

import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.query.repository.CategoryRepository;
import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.MemberUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class CategoryOneService {

  private final CategoryRepository categoryRepository;

  /**
   * 카테고리 엔티티 단일 조회
   *
   * @param categoryId 카테고리 ID
   * @return 결제 엔티티
   */
  @Transactional(readOnly = true)
  public Category getOne(Long categoryId, CategoryType type) {
    Category category = categoryRepository.findByCategoryIdAndType(categoryId, type)
                                          .orElseThrow(() -> new ValidationException("category.not_found"));

    if (!category.getMember().getMemberSeq().equals(MemberUtils.getMemberSeq())) {
      throw new ValidationException("category.not_found");
    }

    return category;
  }
}
