package com.sun.fintrack.common.excel;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelFile<T> {

  private static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;

  private SXSSFWorkbook wb;
  private Sheet sheet;

  public ExcelFile(String fileName, List<T> data) {
    validateMaxRow(data);
    this.wb = new SXSSFWorkbook();
    wb.setCompressTempFiles(true);
    this.sheet = this.wb.createSheet(fileName);
  }

  public void setBody(String[] columns, Object data, int rowIndex, int columnStartIndex) {
    Row row = sheet.createRow(rowIndex);
    int columnIndex = columnStartIndex;
    for (String fieldName : columns) {
      Cell cell = row.createCell(columnIndex++);
      try {
        Field field = data.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        renderCellValue(cell, field.get(data));
      } catch (Exception e) {
        throw new IllegalArgumentException(e.getMessage(), e);
      }
    }
  }

  public void setHeaders(int rowIndex, int columnStartIndex, List<String> fieldName) {
    Row row = sheet.createRow(rowIndex);
    int columnIndex = columnStartIndex;

    for (String headerName : fieldName) {
      Cell cell = row.createCell(columnIndex++);
      cell.setCellValue(headerName);
    }
  }

  public void write(OutputStream stream) throws IOException {
    wb.write(stream);
    wb.close();
    wb.dispose();
    stream.close();
  }

  private void renderCellValue(Cell cell, Object cellValue) {
    if (cellValue instanceof Number) { // (4)
      Number numberValue = (Number) cellValue;
      cell.setCellValue(numberValue.doubleValue());
      return;
    }
    cell.setCellValue(cellValue == null ? "" : cellValue.toString());
  }

  private void validateMaxRow(List<T> data) { // (2)
    int maxRows = supplyExcelVersion.getMaxRows();
    if (data.size() > maxRows) {
      throw new IllegalArgumentException(
          String.format("This concrete ExcelFile does not support over %s rows", maxRows));
    }
  }
}