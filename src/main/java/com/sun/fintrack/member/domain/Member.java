package com.sun.fintrack.member.domain;

import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;
import com.sun.fintrack.common.domain.BaseTimeEntity;
import com.sun.fintrack.member.domain.enums.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {

  /**
   * 회원 일련번호
   */
  @Id
  @Column(name = "MB_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberSeq;
  /**
   * 이메일
   */
  @Column(name = "MB_EMAIL", nullable = false)
  private String email;
  /**
   * 닉네임
   */
  @Column(name = "MB_NM", nullable = false)
  private String name;
  /**
   * 회원 역할
   */
  @Convert(converter = RoleType.TypeCodeConverter.class)
  @Column(name = "MB_ROLE", nullable = false)
  private RoleType role = RoleType.MEMBER;
  /**
   * 유효여부
   */
  @Column(name = "MB_VALID", nullable = false)
  private String valid = "Y";

  public Member(KakaoMemberResponse memberInfo) {
    this.email = memberInfo.email();
    this.name = memberInfo.name();
  }
}
