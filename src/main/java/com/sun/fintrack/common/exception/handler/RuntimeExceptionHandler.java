package com.sun.fintrack.common.exception.handler;

import com.sun.fintrack.common.response.ErrorResponse;
import com.sun.fintrack.common.slack.SlackLogAppender;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile("dev")
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RuntimeExceptionHandler extends ResponseEntityExceptionHandler {

  private final SlackLogAppender slackLogAppender;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> runtimeException(Exception e, HttpServletRequest request) {
    log.info("Exception: " + e.getClass().getSimpleName() + "(" + e.getLocalizedMessage() + ")");
    slackLogAppender.sendErrorLog(e, request);

    return ResponseEntity.ok(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 에러"));
  }
}
