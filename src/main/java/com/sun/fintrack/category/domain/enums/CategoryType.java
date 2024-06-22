package com.sun.fintrack.category.domain.enums;

import com.sun.fintrack.common.domain.converter.AbstractEnumCodeConverter;
import com.sun.fintrack.common.domain.enums.Codable;

import java.util.Arrays;

import jakarta.persistence.Converter;
import lombok.Getter;

/**
 * 카테고리 타입
 */
@Getter
public enum CategoryType implements Codable {

  /**
   * 결제
   */
  PAYMENT("P"),
  /**
   * 수입
   */
  INCOME("I");

  private final String code;

  CategoryType(String code) {
    this.code = code;
  }

  public static boolean containCode(String code) {
    return Arrays.stream(CategoryType.values()).anyMatch(type -> type.code.equals(code));
  }

  public static CategoryType fromCode(String code) {
    return Codable.fromCode(CategoryType.class, code);
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<CategoryType> {
    @Override
    public CategoryType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(CategoryType.class, dbData);
    }
  }
}
