package com.sun.fintrack.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.ZoneId;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Jackson 유틸
 */
@Slf4j
public class JacksonUtils {

  private final ObjectMapper mapper;

  private JacksonUtils() {
    mapper = Jackson2ObjectMapperBuilder.json()
                                        .timeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")))
                                        .serializationInclusion(NON_NULL)
                                        .featuresToEnable(SerializationFeature.INDENT_OUTPUT,
                                            DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                                            MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
                                            MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS,
                                            MapperFeature.DEFAULT_VIEW_INCLUSION,
                                            JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                                        .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS,
                                            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                        .modules(new JavaTimeModule())
                                        .build();
  }

  public static JacksonUtils getInstance() {
    return new JacksonUtils();
  }

  /**
   * 오브젝트를 문자열로 변환
   *
   * @param value 문자열로 변환할 오브젝트
   * @return 문자열
   */
  public static String toJson(Object value) {
    if (value == null) {
      return null;
    }
    try {
      return getMapper().writeValueAsString(value);
    } catch (IOException e) {
      log.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
    }

    return null;
  }

  private static ObjectMapper getMapper() {
    return getInstance().mapper;
  }
}
