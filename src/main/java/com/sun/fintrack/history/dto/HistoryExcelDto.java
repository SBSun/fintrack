package com.sun.fintrack.history.dto;

import com.sun.fintrack.common.excel.ExcelColumn;

/**
 * 내역 엑셀 데이터 형식
 */
public class HistoryExcelDto {

  @ExcelColumn(headerName = "거래 내용")
  private String content;

  @ExcelColumn(headerName = "거래 금액")
  private Long price;

  @ExcelColumn(headerName = "거래일시")
  private String tradeDt;

  @ExcelColumn(headerName = "자산명")
  private String assetName;

  @ExcelColumn(headerName = "카테고리명")
  private String categoryName;

  public HistoryExcelDto(String content, Long price, String tradeDt, String assetName, String categoryName) {
    this.content = content;
    this.price = price;
    this.tradeDt = tradeDt;
    this.assetName = assetName;
    this.categoryName = categoryName;
  }
}
