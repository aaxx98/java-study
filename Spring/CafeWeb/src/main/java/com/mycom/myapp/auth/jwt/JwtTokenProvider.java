package com.mycom.myapp.auth.jwt;

import com.mycom.myapp.common.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtTokenProvider {

  private final Key key;

  public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  // 토큰 생성
  public String createToken(Long id, String email, String name) {
    Date now = new Date(); // 발급 시간
    Date exp = new Date(now.getTime() + 1000 * 60 * 60 * 3); // 만료시간: 3시간

    return Jwts.builder()
        .setSubject(String.valueOf(email)) // 토큰의 subject = 사용자 이메일
        .claim("id", id)
        .claim("name", name) // 커스텀 claim
        .setIssuedAt(now) // 발급 시간
        .setExpiration(exp) // 만료시간
        .signWith(key, SignatureAlgorithm.HS256) // 비밀키로 서명
        .compact(); // 문자열 형태의 JWT 생성
  }

  // 토큰 검사
  public void validate(String token) {
    try {
      Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      throw new UnauthorizedException("토큰이 만료되었습니다.");
    } catch (JwtException e) {
      throw new UnauthorizedException("유효하지 않은 토큰입니다.");
    }
  }

  // 사용자 정보 반환
  public Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(key).build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String getUserEmail(String token) {
    return getClaims(token).getSubject();
  }

  public String getUserName(String token) {
    return getClaims(token).get("name", String.class);
  }
}
