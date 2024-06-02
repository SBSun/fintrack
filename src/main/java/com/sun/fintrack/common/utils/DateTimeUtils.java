package com.sun.fintrack.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;

/**
 * 날짜/시간 유틸
 */
@UtilityClass
public class DateTimeUtils {

  /**
   * 기본 DateTime 형식 (yyyy-MM-dd HH:mm:ss)
   */
  public final String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";

  /**
   * LocalDateTime -> yyyy-MM-dd HH:mm:ss 문자열 변환
   */
  public String convertToString(LocalDateTime dateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return dateTime.format(formatter);
  }
}
