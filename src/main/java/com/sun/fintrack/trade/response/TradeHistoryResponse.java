package com.sun.fintrack.trade.response;

import com.sun.fintrack.common.utils.DateTimeUtils;
import com.sun.fintrack.trade.domain.enums.TradeType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * 거래 내역 조회 반환 형식
 */
@Getter
public class TradeHistoryResponse {

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

  public TradeHistoryResponse(List<History> list) {
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
    // 전체 통계 (모든 항목 포함)
    this.all = new Stats(deposit.count + withdraw.count, deposit.totalAmount - withdraw.totalAmount);
  }

  public void setImageUrl(String s3Url) {
    list.forEach(h -> h.setImage(s3Url + h.getImage()));
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
     * 내역 이미지
     */
    private String image;
    /**
     * 거래일시
     */
    private String tradeDt;
    /**
     * 거래 타입
     */
    private String type;
    /**
     * 카테고리 일련번호
     */
    private Long categorySeq;
    /**
     * 카테고리명
     */
    private String categoryName;

    public History(Long tradeSeq, String content, Long price, String image, LocalDateTime tradeDt, String type,
        Long categorySeq, String categoryName) {
      this.tradeSeq = tradeSeq;
      this.content = content;
      this.price = price;
      this.image = image;
      this.tradeDt = DateTimeUtils.formatLocalDateTime(tradeDt);
      this.type = type;
      this.categorySeq = categorySeq;
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
