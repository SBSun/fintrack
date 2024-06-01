package com.sun.fintrack.member.domain.enums;

import com.sun.fintrack.common.domain.converter.AbstractEnumCodeConverter;
import com.sun.fintrack.common.domain.enums.Codable;

import jakarta.persistence.Converter;
import lombok.Getter;

/**
 * 회원 역할 타입
 */
@Getter
public enum RoleType implements Codable {

  /**
   * 회원
   */
  MEMBER("M"),
  /**
   * 관리자
   */
  ADMIN("A");

  private final String code;

  RoleType(String code) {
    this.code = code;
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<RoleType> {
    @Override
    public RoleType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(RoleType.class, dbData);
    }
  }
}
