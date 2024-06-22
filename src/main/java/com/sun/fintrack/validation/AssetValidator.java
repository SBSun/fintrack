package com.sun.fintrack.validation;

import com.sun.fintrack.asset.request.AssetEntryRequest;
import com.sun.fintrack.asset.request.AssetModifyRequest;
import com.sun.fintrack.common.exception.ValidationException;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 자산 요청 파라미터 검증
 */
@UtilityClass
public class AssetValidator {

  /**
   * 자산 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(AssetEntryRequest param) {
    // 자산명
    validateEmpty(param.getName(), "asset.param_name_empty");
    // 자산 금액
    if (Objects.isNull(param.getAmount())) {
      throw new ValidationException("asset.param_amount_empty");
    }
  }

  /**
   * 자산 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(AssetModifyRequest param) {
    // 자산 일련번호
    validateSeq(param.getAssetSeq());
    // 자산 내용
    validateEmpty(param.getName(), "asset.param_name_empty");
    // 자산 금액
    if (Objects.isNull(param.getAmount())) {
      throw new ValidationException("asset.param_amount_empty");
    }
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
   * 자산 일련번호 유효성 체크
   *
   * @param assetSeq 자산 일련번호
   */
  public void validateSeq(Long assetSeq) {
    if (Objects.isNull(assetSeq)) {
      throw new ValidationException("asset.param_seq_empty");
    }
  }
}
