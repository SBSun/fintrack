package com.sun.fintrack.history.response;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.history.enums.HistoryType;
import com.sun.fintrack.trade.domain.enums.TradeType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * 내역 조회 반환 형식
 */
@Getter
public class HistoryResponse {

  /**
   * 내역 목록
   */
  private final List<History> list;
  /**
   * 전체 통계
   */
  private final Stats all;
  /**
   * 입금 내역 통계
   */
  private final Stats deposit;
  /**
   * 출금 내역 통계
   */
  private final Stats withdraw;
  /**
   * 이체 내역 통계
   */
  private final Stats transfer;

  public HistoryResponse(List<History> list) {
    this.list = list;

    // 타입별 통계 계산
    Map<String, Stats> statsMap = list.stream()
                                      .filter(history -> history.getType() != null)
                                      .collect(Collectors.groupingBy(History::getType,
                                          Collectors.collectingAndThen(Collectors.toList(), this::calculateStats)));

    // 입금 내역 통계
    this.deposit = statsMap.getOrDefault(TradeType.DEPOSIT.getCode(), new Stats(0L, 0L));
    // 출금 내역 통계
    this.withdraw = statsMap.getOrDefault(TradeType.WITHDRAW.getCode(), new Stats(0L, 0L));
    // 이체 내역 통계
    this.transfer = statsMap.getOrDefault(HistoryType.TRANSFER.getCode(), new Stats(0L, 0L));
    // 전체 통계 (모든 항목 포함)
    this.all = new Stats(deposit.count + withdraw.count + transfer.count, deposit.totalAmount - withdraw.totalAmount);
  }

  private Stats calculateStats(List<History> list) {
    // 개수와 합계 금액 계산
    long count = list.size();
    long totalAmount = list.stream().mapToLong(History::getPrice).sum();
    return new Stats(count, totalAmount);
  }

  @Getter
  @Setter
  public static class History {
    /**
     * 거래 일련번호
     */
    private Long tradeSeq;
    /**
     * 거래 내용
     */
    private String content;
    /**
     * 거래 금액
     */
    private Long price;
    /**
     * 거래일시
     */
    private String tradeDt;
    /**
     * 이력 타입
     */
    private String type;
    /**
     * 자산명
     */
    private String assetName;
    /**
     * 카테고리명
     */
    private String categoryName;

    public History(Long tradeSeq, String content, Long price, LocalDateTime tradeDt, String type, String assetName,
        String categoryName) {
      this.tradeSeq = tradeSeq;
      this.content = content;
      this.price = price;
      this.tradeDt = DateTimeUtils.formatLocalDateTime(tradeDt);
      this.type = type;
      this.assetName = assetName;
      this.categoryName = categoryName;
    }
  }

  @Getter
  @Setter
  public static class Stats {
    /**
     * 내역 개수
     */
    private Long count;
    /**
     * 합계 금액
     */
    private Long totalAmount;

    public Stats(Long count, Long totalAmount) {
      this.count = count;
      this.totalAmount = totalAmount;
    }
  }
}
