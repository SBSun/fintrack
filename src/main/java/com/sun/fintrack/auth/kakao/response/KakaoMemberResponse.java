package com.sun.fintrack.auth.kakao.response;

/**
 * 회원 정보 요청에 대한 응답 정보
 *
 * @author 송병선
 */
public record KakaoMemberResponse(

    String email, String name) {

}
