package com.sun.fintrack.income.command.service;

import com.sun.fintrack.income.domain.Income;
import com.sun.fintrack.income.query.repository.IncomeRepository;
import com.sun.fintrack.income.query.service.IncomeOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 소득 삭제 서비스
 */
@RequiredArgsConstructor
@Service
public class IncomeDeleteService {

  private final IncomeOneService incomeOneService;
  private final IncomeRepository incomeRepository;

  /**
   * 소득 삭제
   *
   * @param incomeSeq 소득 일련번호
   */
  @Transactional
  public void delete(Long incomeSeq) {
    Income income = incomeOneService.getOne(incomeSeq);
    incomeRepository.delete(income);
  }
}
