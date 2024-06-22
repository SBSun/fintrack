package com.sun.fintrack.common.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * CSV 파일 읽기 유틸
 */
@Slf4j
@UtilityClass
public class CsvReadUtils {

  public List<String[]> readCSVFile(String fileName) {
    try {
      CSVReader reader = new CSVReader(new FileReader("src/main/resources/" + fileName));
      List<String[]> lines = reader.readAll();
      reader.close();
      return lines;
    } catch (IOException | CsvException e) {
      log.info(e.getMessage());
      throw new IllegalArgumentException("CSV 파일을 읽는 도중 예외가 발생했습니다.");
    }
  }
}
