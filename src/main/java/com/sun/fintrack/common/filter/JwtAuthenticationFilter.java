package com.sun.fintrack.common.filter;

import com.sun.fintrack.auth.service.JwtService;
import com.sun.fintrack.common.config.jwt.JwtProperties;
import com.sun.fintrack.common.utils.CookieUtils;
import com.sun.fintrack.member.domain.MemberDetail;
import com.sun.fintrack.member.service.MemberOneService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final MemberOneService memberOneService;
  private final JwtProperties jwtProperties;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String accessToken = CookieUtils.getCookie(request, jwtProperties.cookieName());

    if (!StringUtils.hasText(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    String memberSeq = jwtService.extractUsername(accessToken);
    if (StringUtils.hasText(memberSeq)) {
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
