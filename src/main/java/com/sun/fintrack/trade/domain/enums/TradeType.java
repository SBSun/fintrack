package com.sun.fintrack.trade.domain.enums;

import com.sun.fintrack.common.domain.converter.AbstractEnumCodeConverter;
import com.sun.fintrack.common.domain.enums.Codable;

import java.util.Arrays;

import jakarta.persistence.Converter;
import lombok.Getter;

/**
 * 거래 타입
 */
@Getter
public enum TradeType implements Codable {

  /**
   * 입금
   */
  DEPOSIT("D"),
  /**
   * 출금
   */
  WITHDRAW("W");

  private final String code;

  TradeType(String code) {
    this.code = code;
  }

  public static boolean containCode(String code) {
    return Arrays.stream(TradeType.values()).anyMatch(type -> type.code.equals(code));
  }

  public static TradeType fromCode(String code) {
    return Codable.fromCode(TradeType.class, code);
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<TradeType> {
    @Override
    public TradeType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(TradeType.class, dbData);
    }
  }
}
