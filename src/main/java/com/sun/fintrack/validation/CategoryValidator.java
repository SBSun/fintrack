package com.sun.fintrack.validation;

import com.sun.fintrack.category.request.CategoryEntryRequest;
import com.sun.fintrack.category.request.CategoryModifyRequest;
import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.trade.domain.enums.TradeType;

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
   * @param categorySeq 카테고리 일련번호
   * @param type        카테고리 타입
   */
  public void validate(Long categorySeq, String type) {
    // 카테고리 일련번호
    validateSeq(categorySeq);
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
    // 카테고리 일련번호
    validateSeq(param.getCategorySeq());
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
   * 카테고리 일련번호 유효성 체크
   *
   * @param categorySeq 카테고리 일련번호
   */
  public void validateSeq(Long categorySeq) {
    if (Objects.isNull(categorySeq)) {
      throw new ValidationException("category.param_seq_empty");
    }
  }

  /**
   * 카테고리 타입 유효성 체크
   *
   * @param type 카테고리 타입
   */
  public void validateType(String type) {
    validateEmpty(type, "category.param_type_empty");
    if (!TradeType.containCode(type)) {
      throw new ValidationException("category.param_type_invalid");
    }
  }
}
