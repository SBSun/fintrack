package com.sun.fintrack.trade.query.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.trade.domain.Trade;
import com.sun.fintrack.trade.domain.enums.TradeType;
import com.sun.fintrack.trade.query.repository.TradeRepository;
import com.sun.fintrack.trade.response.TradeDetailResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 거래 정보 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class TradeOneService {

  private final TradeRepository tradeRepository;

  /**
   * 거래 단일 상세 조회
   *
   * @param tradeSeq 거래 일련번호
   * @return 거래 엔티티
   */
  @Transactional(readOnly = true)
  public TradeDetailResponse getDetail(Long tradeSeq, String type) {
    return new TradeDetailResponse(getOne(tradeSeq, TradeType.fromCode(type)));
  }

  /**
   * 거래 엔티티 단일 조회
   *
   * @param tradeSeq 거래 일련번호
   * @return 거래 엔티티
   */
  @Transactional(readOnly = true)
  public Trade getOne(Long tradeSeq, TradeType type) {
    return tradeRepository.findByTradeSeqAndType(tradeSeq, type)
                          .orElseThrow(() -> new ValidationException("trade.not_found"));
  }
}
