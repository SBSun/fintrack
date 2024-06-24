package com.sun.fintrack.transfer.query.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.transfer.domain.Transfer;
import com.sun.fintrack.transfer.query.repository.TransferRepository;
import com.sun.fintrack.transfer.response.TransferDetailResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 이체 정보 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class TransferOneService {

  private final TransferRepository transferRepository;

  /**
   * 이체 단일 상세 조회
   *
   * @param transferSeq 이체 일련번호
   * @return 이체 엔티티
   */
  @Transactional(readOnly = true)
  public TransferDetailResponse getDetail(Long transferSeq) {
    return new TransferDetailResponse(getOne(transferSeq));
  }

  /**
   * 이체 엔티티 단일 조회
   *
   * @param transferSeq 이체 일련번호
   * @return 이체 엔티티
   */
  @Transactional(readOnly = true)
  public Transfer getOne(Long transferSeq) {
    return transferRepository.findById(transferSeq).orElseThrow(() -> new ValidationException("transfer.not_found"));
  }
}
