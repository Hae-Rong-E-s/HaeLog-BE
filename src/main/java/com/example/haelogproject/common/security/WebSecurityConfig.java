package com.example.haelogproject.common.security;

import com.example.haelogproject.common.jwt.JwtAuthFilter;
import com.example.haelogproject.common.jwt.JwtUtil;
import com.example.haelogproject.common.jwt.exception.JwtExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. CSRF(Cross-site request forgery) 비활성화 설정 및 cors 설정
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource());

        // 2. Session 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 3. Request에 대한 인증/인가
        http.authorizeRequests().
                // 3-1. Authentication 예외 처리
                // 3-1-1. SignUp, Login API 인증 예외 처리
                antMatchers("/api/member/signup").permitAll().
                antMatchers("/api/member/login").permitAll().
                antMatchers("/api/member/signup/loginid").permitAll().
                antMatchers("/api/member/signup/nickname").permitAll().
                antMatchers("/api/{nickname}").permitAll().
                antMatchers("/api/{nickname}/post").permitAll().
                antMatchers("/api").permitAll().
                antMatchers("/").permitAll().
                requestMatchers(CorsUtils::isPreFlightRequest).permitAll(). // pre-flight 요청 무시하기
                anyRequest().authenticated();

        // 4. Filter 등록
        // 4-1. JWT Filter 등록
        http.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionHandlerFilter(), JwtAuthFilter.class);
        // 4-2. OAuth Filter 등록

        return http.build();
    }

    // cors 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin("http://localhost:3002");
        configuration.addAllowedOrigin("http://localhost:3003");
        configuration.addAllowedOrigin("http://localhost:3004");
        configuration.addAllowedOrigin("http://localhost:3005");
        configuration.addAllowedOrigin("https://hae-log-rdyyfm5ie-haelog-fe.vercel.app/");
        configuration.addAllowedOrigin("https://hae-log-fe.vercel.app/");

        //나중에 프론트 서버 origin으로 변경해야함
//            configuration.addAllowedOrigin("http://soribaddah.s3-website.ap-northeast-2.amazonaws.com");
        // 예비 요청 - 본 요청 프론트와의 트러블 슈팅 -> OPTIONS 추가
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS", "PUT")); // 허용할 Http Method
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // 내 서버가 응답할 때 json을 js에서 처리할 수 있게 설정
        configuration.setMaxAge(3600L);
        configuration.addExposedHeader("authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
