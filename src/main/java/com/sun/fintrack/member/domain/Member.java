package com.sun.fintrack.member.domain;

import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;
import com.sun.fintrack.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 엔티티
 *
 * @author 송병선
 */
@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

  /**
   * 회원 ID
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
  @Column(name = "MB_NAME", nullable = false)
  private String name;
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
