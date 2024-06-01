package com.sun.fintrack.auth.service;

import com.sun.fintrack.common.config.jwt.JwtProperties;
import com.sun.fintrack.member.domain.Member;
import com.sun.fintrack.member.domain.MemberDetail;
import com.sun.fintrack.member.service.MemberOneService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Jwt 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {

  private Key key;
  private final JwtProperties jwtProperties;
  private final MemberOneService memberOneService;

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(Member member) {
    final Map<String, Object> claims = Map.of("name", member.getName(), "email", member.getEmail());
    return generateToken(claims, new MemberDetail(member));
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
               .setClaims(extraClaims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.expirationTime()))
               .signWith(key, SignatureAlgorithm.HS512)
               .compact();
  }

  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

}
