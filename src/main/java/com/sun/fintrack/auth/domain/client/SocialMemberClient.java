package com.sun.fintrack.auth.domain.client;

import com.sun.fintrack.auth.domain.enums.SocialType;
import com.sun.fintrack.auth.kakao.response.KakaoMemberResponse;

/**
 * Resource Server 회원 정보 요청
 *
 * @author 송병선
 */
public interface SocialMemberClient {

  /**
   * @return 회원 정보
   */
  KakaoMemberResponse fetch(String authCode);

  /**
   * @return 지원하는 SocialType
   */
  SocialType supportSocial();
}
