package com.example.outsourcingproject.auth.filter;

import com.example.outsourcingproject.common.utils.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j(topic = "Jwtfilter")
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    private static final String[] SIGN_UP_URI = {
        "/auth/sign-up/customers",
        "/auth/sign-up/owners"
    };

    private static final String[] SIGN_IN_URI = {
        "/auth/sign-in/customers",
        "/auth/sign-in/owners"
    };


    @Override
    public void doFilter(
        ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain
    )
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();
        String email = null;
        String jwtToken = null;

        String authorizationHeader = httpRequest.getHeader("Authorization");

        log.info("Jwt Filter 로직 실행");
        log.info("request URI: {}", requestURI);

        /**
         * (1) 회원가입 또는 로그인 URI
         */
        boolean isSignUpOrSignInURI = isSignUpURI(requestURI) || isSignInURI(requestURI);
        if (isSignUpOrSignInURI) {

            // 토큰이 없음 -> 통과
            boolean isInvalidAuthorizationHeader =
                authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
            if (isInvalidAuthorizationHeader) {
                log.info("JWT 토큰 없음");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 토큰이 있음 -> todo exception
            log.info("토큰을 가진 상태에서 회원가입 또는 로그인을 할 수 없음");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("{\"error\": \"FORBIDDEN\"}"); //todo
            return;
        }

        /**
         * (2) 회원가입, 로그인 URI 아닐 때, JWT 토큰 있는지 검사
         */

        // 토큰이 없는 경우
        log.info("authorizaionHeader : {}", authorizationHeader);
        boolean isInvalidAuthorizationHeader =
            authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
        if (isInvalidAuthorizationHeader) {
            log.info("JWT 토큰이 필요합니다.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다."); //todo
            return;
        }

        //토큰이 있는 경우 -> 유효성 검사 (secret key가 내가 만든 거랑 동일한지, Jwt 시간 만료된게 아닌지
        jwtToken = authorizationHeader.substring(7);
        log.info("jwtToken : {}", jwtToken);

        // 유효하지 않은 case
        boolean isValidateToken = jwtUtil.validateToken(jwtToken);
        if (!isValidateToken) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("{\"error\": \"Unauthorized\"}"); // todo
            return;
        }

        // 통과

        // 요청을 보낸 유저의 권한 확인하기
        String authority = jwtUtil.extractAuthority(jwtToken);
        servletRequest.setAttribute("authority", authority); // OWNER 또는 CUSTOMER

        filterChain.doFilter(servletRequest, servletResponse);

    }

    // requestURI가 회원가입 URI인지 확인
    public boolean isSignUpURI(String requestURI) {
        return PatternMatchUtils.simpleMatch(SIGN_UP_URI, requestURI);
    }

    public boolean isSignInURI(String requestURI) {
        return PatternMatchUtils.simpleMatch(SIGN_IN_URI, requestURI);
    }

}
