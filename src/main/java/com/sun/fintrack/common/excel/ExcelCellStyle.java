package com.sun.fintrack.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 엑셀 셀 스타일
 */
public interface ExcelCellStyle {

  void apply(CellStyle cellStyle);
  

}
