package com.sun.fintrack.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.experimental.UtilityClass;

/**
 * 날짜/시간 유틸
 */
@UtilityClass
public class DateTimeUtils {

  /**
   * 기본 Date 형식 (yyyy-MM-dd)
   */
  public final String DEFAULT_DATE = "yyyy-MM-dd";
  /**
   * 기본 DateTime 형식 (yyyy-MM-dd HH:mm:ss)
   */
  public final String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";

  /**
   * yyyy-MM-dd 문자열 -> LocalDate
   */
  public LocalDate convertToDate(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE, Locale.KOREA);
    return LocalDate.parse(dateString, formatter);
  }

  /**
   * LocalDateTime -> yyyy-MM-dd HH:mm:ss 문자열 변환
   */
  public String convertToString(LocalDateTime dateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    return dateTime.format(formatter);
  }

  /**
   * 날짜 문자열 포맷 유효성 체크
   *
   * @param dateString 날짜 문자열
   * @param pattern    패턴
   * @return 유효여부
   */
  public boolean validFormat(String dateString, String pattern) {
    if (StringUtils.isBlank(dateString)) {
      return false;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    try {
      if (StringUtils.containsAny(pattern, "H", "m", "s", "S")) {
        LocalDateTime.parse(dateString, formatter);
      } else {
        LocalDate.parse(dateString, formatter);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
