package com.sun.fintrack.category.command.service;

import com.sun.fintrack.category.domain.Category;
import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.query.repository.CategoryRepository;
import com.sun.fintrack.category.query.service.CategoryOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 삭제 서비스
 */
@RequiredArgsConstructor
@Service
public class CategoryDeleteService {

  private final CategoryRepository categoryRepository;
  private final CategoryOneService categoryOneService;

  /**
   * 카테고리 삭제
   *
   * @param categoryId 카테고리 ID
   */
  @Transactional
  public void delete(Long categoryId, String type) {
    Category category = categoryOneService.getOne(categoryId, CategoryType.fromCode(type));
    categoryRepository.delete(category);
  }
}
