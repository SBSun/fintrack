package com.sun.fintrack.member.service;

import com.sun.fintrack.common.exception.ValidationException;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 회원 단일 조회 서비스
 */
@RequiredArgsConstructor
@Service
public class MemberOneService {

  private final MemberRepository memberRepository;

  @Transactional(readOnly = true)
  public Member getOne(Long memberSeq) {
    return memberRepository.findById(memberSeq).orElseThrow(() -> new ValidationException("member.not_found"));
  }
}
