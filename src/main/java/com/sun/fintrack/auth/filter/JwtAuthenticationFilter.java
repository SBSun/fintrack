package com.sun.fintrack.auth.filter;

import com.sun.fintrack.auth.service.JwtService;
import com.sun.fintrack.common.config.jwt.JwtProperties;
import com.sun.fintrack.common.utils.CookieUtils;
import com.sun.fintrack.member.domain.MemberDetail;
import com.sun.fintrack.member.service.MemberOneService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JwtAuthenticationFilter
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final JwtProperties jwtProperties;
  private final MemberOneService memberOneService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String accessToken = CookieUtils.getCookie(request, jwtProperties.cookieName());

    if (StringUtils.isBlank(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    String memberSeq = jwtService.extractUsername(accessToken);
    if (!StringUtils.isBlank(memberSeq)) {
      MemberDetail memberDetail = new MemberDetail(memberOneService.getOne(Long.valueOf(memberSeq)));
      // 유효성 체크
      if (jwtService.isTokenValid(accessToken, memberDetail)) {
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(memberDetail, accessToken, memberDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }
}
