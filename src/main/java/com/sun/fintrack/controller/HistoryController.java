package com.sun.fintrack.controller;

import com.sun.fintrack.common.excel.ExcelColumn;
import com.sun.fintrack.common.excel.ExcelFile;
import com.sun.fintrack.common.response.DataResponse;
import com.sun.fintrack.common.utils.MemberUtils;
import com.sun.fintrack.history.dto.HistoryExcelDto;
import com.sun.fintrack.history.query.service.HistoryListService;
import com.sun.fintrack.history.query.service.HistoryOneService;
import com.sun.fintrack.history.request.HistoryMonthlyRequest;
import com.sun.fintrack.validation.HistoryValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 내역 api
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/histories")
@RestController
public class HistoryController {

  private final HistoryOneService historyOneService;
  private final HistoryListService historyListService;

  /**
   * 일일 내역 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/daily")
  public ResponseEntity<?> doGetDaily(@RequestParam(required = false) String type,
      @RequestParam(required = false) String date) {
    HistoryValidator.validateDaily(type, date);

    return ResponseEntity.ok(new DataResponse(historyOneService.getOne(type, date, MemberUtils.getMemberSeq())));
  }

  /**
   * 월별 내역 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/monthly")
  public ResponseEntity<?> doGetMonthly(@ModelAttribute HistoryMonthlyRequest param) {
    HistoryValidator.validate(param);

    return ResponseEntity.ok(new DataResponse(historyOneService.getOne(param)));
  }

  /**
   * 월별 내역 조회 엑셀 다운로드
   *
   * @return 요청 결과
   */
  @GetMapping("/monthly/excel")
  public void doGetMonthlyExcel(@ModelAttribute HistoryMonthlyRequest param, HttpServletResponse response)
      throws IOException {
    HistoryValidator.validate(param);

    List<HistoryExcelDto> historyExcelDtoList = historyListService.getList(param);
    downloadExcel(historyExcelDtoList, response);
  }

  /**
   * 내역 내용 검색 조회
   *
   * @return 요청 결과
   */
  @GetMapping("/search")
  public ResponseEntity<?> doGetSearch(@RequestParam(required = false) String keyword) {
    return ResponseEntity.ok(new DataResponse(historyOneService.getOne(keyword)));
  }

  private void downloadExcel(List<HistoryExcelDto> data, HttpServletResponse response) throws IOException {
    List<String> headers = Arrays.stream(HistoryExcelDto.class.getDeclaredFields())
                                 .map(v -> v.getAnnotation(ExcelColumn.class).headerName())
                                 .toList();

    String[] columns =
        Arrays.stream(HistoryExcelDto.class.getDeclaredFields()).map(Field::getName).toList().toArray(String[]::new);

    ExcelFile<HistoryExcelDto> file = new ExcelFile<>("월별 내역", data);

    file.setHeaders(0, 0, headers);

    int rowIdx = 1;
    for (Object rowData : data) {
      file.setBody(columns, rowData, rowIdx++, 0);
    }

    response.setContentType("ms-vnd/excel");
    response.setHeader("Content-Disposition",
        "attachment;filename=" + URLEncoder.encode("월별 내역.xlsx", StandardCharsets.UTF_8));

    file.write(response.getOutputStream());
  }
}
