package com.example.haelogproject.common.jwt;

import com.example.haelogproject.common.jwt.exception.CustomSecurityException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.haelogproject.common.exception.ExceptionMessage.INVALID_TOKEN_MSG;
import static com.example.haelogproject.common.exception.ExceptionMessage.TOKEN_NOT_FOUND_MSG;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, CustomSecurityException {
        // Filter가 적용되고 있는 uri 추출
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Login, SignUp, checkLoginId, checkNickname 전체 조회 API의 경우 해당 Filter 건너뜀.
        if (uri.equals("/api/member/login")
                || uri.equals("/api/member/signup")
                || uri.equals("/api/member/signup/loginid")
                || uri.equals("/api/member/signup/nickname")
                || uri.equals("/api/{nickname}")
                || uri.equals("/api/{nickname}/post")
                || uri.equals("/api")
                || uri.equals("/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Post 조회 관련 API 일때 해당 Filter 건너뜀
        if (uri.contains("/api") && method.equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        // pre-flight 요청일 때, 해당 Filter 건너뜀.
        if (method.equals("OPTIONS")) {
            return;
        }

        // 1. Request에서 토큰 추출
        String token = jwtUtil.resolveToken(request, "authorization");

        // 2. Token 유효성 검사 및 인증
        // 2-1. Token 존재 여부 확인
        if (token == null) {
            throw new CustomSecurityException(TOKEN_NOT_FOUND_MSG);
        }
        // 2-2. Token 유효성 확인
        if (!jwtUtil.validateAccessToken(token, request, response)) {
            throw new CustomSecurityException(INVALID_TOKEN_MSG);
        }
        // 3. 사용자 인증
        Claims info = jwtUtil.getUserInfoFromHttpServletRequest(request);
        setAuthentication(info.getSubject());

        // 4. 다음 필터로 보냄
        filterChain.doFilter(request, response);
    }

    // 인증/인가 설정
    private void setAuthentication(String loginId) {
        // 1. Security Context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // 2. Authentication 생성
        Authentication authentication = jwtUtil.createAuthentication(loginId);
        // 3. Context에 Authentication 넣기
        context.setAuthentication(authentication);
        // 4. Security Context Holder에 Context 넣기
        SecurityContextHolder.setContext(context);
    }
}
