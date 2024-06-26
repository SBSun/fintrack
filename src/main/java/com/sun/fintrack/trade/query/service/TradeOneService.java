package com.sun.fintrack.trade.query.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.trade.domain.Trade;
import com.sun.fintrack.trade.domain.enums.TradeType;
import com.sun.fintrack.trade.query.dao.TradeListDao;
import com.sun.fintrack.trade.query.repository.TradeRepository;
import com.sun.fintrack.trade.request.TradeStatsRequest;
import com.sun.fintrack.trade.response.TradeDetailResponse;
import com.sun.fintrack.trade.response.TradeStatsResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 거래 정보 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class TradeOneService {

  @Value("${cloud.aws.s3.url}")
  private String s3ImageUrl;

  private final TradeRepository tradeRepository;
  private final TradeListDao tradeListDao;

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
   * 거래 내역 카테고리별 통계 조회
   *
   * @return 요청 결과
   */
  @Transactional(readOnly = true)
  public TradeStatsResponse getOne(TradeStatsRequest param) {
    return tradeListDao.select(param);
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
