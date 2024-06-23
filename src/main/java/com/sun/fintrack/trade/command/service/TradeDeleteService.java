package com.sun.fintrack.trade.command.service;

import com.sun.fintrack.trade.domain.Trade;
import com.sun.fintrack.trade.domain.enums.TradeType;
import com.sun.fintrack.trade.query.repository.TradeRepository;
import com.sun.fintrack.trade.query.service.TradeOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 거래 삭제 서비스
 */
@RequiredArgsConstructor
@Service
public class TradeDeleteService {

  private final TradeOneService tradeOneService;
  private final TradeRepository tradeRepository;

  /**
   * 거래 삭제
   *
   * @param tradeSeq 거래 일련번호
   * @param type     거래 타입
   */
  @Transactional
  public void delete(Long tradeSeq, String type) {
    Trade trade = tradeOneService.getOne(tradeSeq, TradeType.fromCode(type));
    tradeRepository.delete(trade);
  }
}
