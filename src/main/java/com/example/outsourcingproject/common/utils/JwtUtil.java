package com.example.outsourcingproject.common.utils;

import com.example.outsourcingproject.common.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    // JWT 토큰 값 앞에 붙는 접두사
    public static final String BEARER_PREFIX = "Bearer ";
    // JWT 토큰 만료 시간 (밀리초 단위)
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
    // JWT 서명 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 애플리케이션 설정 파일에서 주입받은 비밀 키
    @Value("${jwt.secret.key}")
    private String secretKey;
    // 실제 서명에 사용되는 키 객체
    private Key key;

    /**
     * Bean 초기화 메서드 - 애플리케이션 시작 시 비밀 키를 Base64로 디코딩하여 Key 객체를 초기화
     */
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String email, Authority authority) {

        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(email) // 사용자 식별자
                // todo 아이디 저장
                .claim("authority", authority) // 사용자 역할
                .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 토큰의 만료시간
                .setIssuedAt(date) // 토큰 발급 시점
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();
    }

    // 토큰에서 클레임 객체를 추출하는 메서드
    private Claims extractAllClaims(String token) {
        log.info("기존 token : {} ", token);
        token = token.replace("Bearer ", ""); // 앞에 붙는 'Bearer ' 제거
        log.info("수정된 token : {} ", token);
        return Jwts.parser()
            .setSigningKey(key) // 비밀 키를 사용하여 서명 검증
            .parseClaimsJws(token)
            .getBody();
    }

    // 토큰에서 손님 이메일 추출
    public String extractCustomerEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰에서 사장님 이메일 추출
    public String extractOwnerEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰에서 유저 권한 추출 (손님 or 사장님)
    public String extractAuthority(String token) {
        log.info("유저 권한 추출 : {}", extractAllClaims(token).get("authority", String.class));
        return extractAllClaims(token).get("authority", String.class);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            // JWT 파서 빌더를 사용하여 토큰의 서명을 검증한다.
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token); // 토큰 파싱 및 검증
            return true; // 토큰이 유효한 경우

        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            // 토큰 서명이 잘못되었더나, 잘못된 형식의 JWT가 전달된 경우
            log.error("유효하지 않는 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            // 토크이 만료된 경우
            log.error("만료된 JWT Token 입니다.");
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 형식이 전달된 경우
            log.error("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            // JWT 클레임이 비어있거나 잘못된 형식일 경우
            log.error("잘못된 JWT 토큰입니다.", e);
        }

        return false; // 토큰이 유효하지 않은 경우
    }

}
