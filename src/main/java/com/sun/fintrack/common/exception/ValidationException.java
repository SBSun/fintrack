package com.sun.fintrack.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 유효성 검사 에러
 *
 * @author 송병선
 */
@Getter
@ToString
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

  private int status = HttpStatus.BAD_REQUEST.value();

  public ValidationException(String message) {
    super("." + message);
  }

  public ValidationException(int status, String message) {
    super("." + message);
    this.status = status;
  }

  @Override
  public String getMessage() {
    return "exception" + super.getMessage();
  }
}
