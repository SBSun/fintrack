package com.sun.fintrack.common.exception.handler;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.common.response.ErrorResponse;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  /**
   * 전체 요청 크기 초과 예외 처리
   */
  @ExceptionHandler(SizeLimitExceededException.class)
  public ResponseEntity<?> handleMaxRequestSizeException(SizeLimitExceededException e) {
    log.info("Exception: " + e.getClass().getSimpleName() + "(" + e.getLocalizedMessage() + ")");
    return ResponseEntity.ok(
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "요청 크기가 너무 큽니다. 요청 크기는 10MB 이하로 제한됩니다."));
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleValidationException(ValidationException e, WebRequest request) {
    log.info("Exception: " + e.getClass().getSimpleName() + "(" + e.getLocalizedMessage() + ")");
    final Locale locale = request.getLocale();
    String message;
    message = messageSource.getMessage(e.getMessage(), null, locale);

    return ResponseEntity.ok(new ErrorResponse(e.getStatus(), message));
  }
}
