package com.sun.fintrack.common.utils;

import com.sun.fintrack.member.domain.MemberDetail;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.experimental.UtilityClass;

/**
 * 회원 유틸
 */
@UtilityClass
public class MemberUtils {

  /**
   * 로그인 회원 정보 조회
   *
   * @return 회원 정보
   */
  public MemberDetail getMember() {
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MemberDetail member) {
      return member;
    }
    return null;
  }

  /**
   * 로그인 회원 seq 조회
   *
   * @return 회원 seq
   */
  public Long getMemberSeq() {
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MemberDetail member) {
      return member.getMemberSeq();
    }
    return null;
  }
}
