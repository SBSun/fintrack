package com.sun.fintrack.common.exception.handler;

import com.sun.fintrack.common.response.ErrorResponse;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class FileUploadExceptionHandler {

  /**
   * 개별 파일 크기 초과 예외 처리
   */
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<?> handleMaxFileSizeException(MaxUploadSizeExceededException e) {
    log.info("Exception: " + e.getClass().getSimpleName() + "(" + e.getLocalizedMessage() + ")");
    return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "파일 크기가 너무 큽니다. 파일 크기는 5MB 이하로 제한됩니다."));
  }

  /**
   * 개별 파일 크기 초과 예외 처리
   */
  @ExceptionHandler(FileSizeLimitExceededException.class)
  public ResponseEntity<?> handleMaxFileSizeException(FileSizeLimitExceededException e) {
    log.info("Exception: " + e.getClass().getSimpleName() + "(" + e.getLocalizedMessage() + ")");
    return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "파일 크기가 너무 큽니다. 파일 크기는 5MB 이하로 제한됩니다."));
  }
}
