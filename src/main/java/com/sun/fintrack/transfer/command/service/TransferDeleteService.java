package com.sun.fintrack.transfer.command.service;

import com.sun.fintrack.transfer.domain.Transfer;
import com.sun.fintrack.transfer.query.repository.TransferRepository;
import com.sun.fintrack.transfer.query.service.TransferOneService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 이체 삭제 서비스
 */
@RequiredArgsConstructor
@Service
public class TransferDeleteService {

  private final TransferOneService transferOneService;
  private final TransferRepository transferRepository;

  /**
   * 이체 삭제
   *
   * @param transferSeq 이체 일련번호
   */
  @Transactional
  public void delete(Long transferSeq) {
    Transfer transfer = transferOneService.getOne(transferSeq);
    transferRepository.delete(transfer);
  }
}
