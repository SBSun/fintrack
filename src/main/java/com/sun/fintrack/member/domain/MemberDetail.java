package com.sun.fintrack.member.domain;

import com.sun.fintrack.member.domain.enums.RoleType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import lombok.Getter;

/**
 * 로그인 회원 정보
 */
@Getter
public class MemberDetail implements UserDetails {

  /**
   * 회원 고유번호
   */
  private final Long memberSeq;
  /**
   * 이메일
   */
  private final String email;
  /**
   * 닉네임
   */
  private final String name;
  /**
   * 회원 역할
   */
  private final RoleType role;

  public MemberDetail(Member member) {
    this.memberSeq = member.getMemberSeq();
    this.name = member.getName();
    this.email = member.getEmail();
    this.role = member.getRole();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.getCode()));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return memberSeq.toString();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
