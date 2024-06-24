package com.sun.fintrack.validation;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.transfer.request.TransferEntryRequest;
import com.sun.fintrack.transfer.request.TransferModifyRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * 이체 요청 파라미터 검증
 */
@UtilityClass
public class TransferValidator {

  /**
   * 이체 등록 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(TransferEntryRequest param) {
    // 이체 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("transfer.param_price_empty");
    }
    // 이체일시
    validateEmpty(param.getTransferDt(), "transfer.param_transfer_dt_empty");
    if (!DateTimeUtils.validFormat(param.getTransferDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 입금 자산 일련변호
    if (Objects.isNull(param.getDepositAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 출금 자산 일련변호
    if (Objects.isNull(param.getWithdrawAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
  }

  /**
   * 이체 수정 요청 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(TransferModifyRequest param) {
    // 이체 일련번호
    if (Objects.isNull(param.getTransferSeq())) {
      throw new ValidationException("transfer.param_seq_empty");
    }
    // 이체 금액
    if (Objects.isNull(param.getPrice())) {
      throw new ValidationException("transfer.param_price_empty");
    }
    // 이체일시
    validateEmpty(param.getTransferDt(), "transfer.param_transfer_dt_empty");
    if (!DateTimeUtils.validFormat(param.getTransferDt(), DateTimeUtils.DEFAULT_DATETIME)) {
      throw new ValidationException("date.param_date_time_invalid");
    }
    // 입금 자산 일련변호
    if (Objects.isNull(param.getDepositAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
    }
    // 출금 자산 일련변호
    if (Objects.isNull(param.getWithdrawAssetSeq())) {
      throw new ValidationException("asset.param_seq_empty");
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
   * 이체 일련번호 유효성 체크
   *
   * @param transferSeq 이체 일련번호
   */
  public void validateSeq(Long transferSeq) {
    if (Objects.isNull(transferSeq)) {
      throw new ValidationException("transfer.param_seq_empty");
    }
  }

  private void validateDate(String date) {
    if (!DateTimeUtils.validFormat(date, DateTimeUtils.DEFAULT_DATE)) {
      throw new ValidationException("date.param_date_invalid");
    }
  }
}
