package com.sun.fintrack.income.query.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.income.domain.Income;
import com.sun.fintrack.income.query.repository.IncomeRepository;
import com.sun.fintrack.income.response.IncomeDetailResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 소득 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class IncomeOneService {

  private final IncomeRepository incomeRepository;

  /**
   * 소득 단일 상세 조회
   *
   * @param incomeSeq 소득 일련번호
   * @return 소득 엔티티
   */
  @Transactional(readOnly = true)
  public IncomeDetailResponse getDetail(Long incomeSeq) {
    return new IncomeDetailResponse(getOne(incomeSeq));
  }

  /**
   * 소득 엔티티 단일 조회
   *
   * @param incomeSeq 소득 일련번호
   * @return 소득 엔티티
   */
  @Transactional(readOnly = true)
  public Income getOne(Long incomeSeq) {
    return incomeRepository.findById(incomeSeq).orElseThrow(() -> new ValidationException("income.not_found"));
  }
}
