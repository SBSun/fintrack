package com.sun.fintrack.validation;

import com.sun.fintrack.category.domain.enums.CategoryType;
import com.sun.fintrack.category.request.CategoryEntryRequest;
import com.sun.fintrack.category.request.CategoryModifyRequest;
import com.sun.fintrack.common.exception.ValidationException;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 카테고리 요청 파라미터 검증
 */
@UtilityClass
public class CategoryValidator {

  /**
   * 카테고리 삭제 요청 유효성 체크
   *
   * @param categoryId 카테고리 ID
   * @param type       카테고리 타입
   */
  public void validate(Long categoryId, String type) {
    // 카테고리 ID
    validateId(categoryId);
    // 카테고리 타입
    validateType(type);
  }

  /**
   * 카테고리 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(CategoryEntryRequest param) {
    // 카테고리명
    validateEmpty(param.getName(), "category.param_name_empty");
    // 카테고리 타입
    validateType(param.getType());
  }

  /**
   * 카테고리 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(CategoryModifyRequest param) {
    // 카테고리 ID
    validateId(param.getCategoryId());
    // 카테고리명
    validateEmpty(param.getName(), "category.param_name_empty");
    // 카테고리 타입
    validateType(param.getType());
  }

  /**
   * 문자열 공백 체크
   *
   * @param str 문자열
   */
  public void validateEmpty(String str, String exception) {
    if (StringUtils.isBlank(str)) {
      throw new ValidationException(exception);
    }
  }

  /**
   * 카테고리 ID 유효성 체크
   *
   * @param categoryId 카테고리 ID
   */
  public void validateId(Long categoryId) {
    if (Objects.isNull(categoryId)) {
      throw new ValidationException("category.param_id_empty");
    }
  }

  /**
   * 카테고리 타입 유효성 체크
   *
   * @param type 카테고리 타입
   */
  public void validateType(String type) {
    validateEmpty(type, "category.param_type_empty");
    if (!CategoryType.containCode(type)) {
      throw new ValidationException("category.param_type_invalid");
    }
  }
}
