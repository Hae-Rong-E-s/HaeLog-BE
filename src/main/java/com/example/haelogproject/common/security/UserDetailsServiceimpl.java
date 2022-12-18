package com.example.haelogproject.common.security;

import com.example.haelogproject.member.entity.Member;
import com.example.haelogproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//인증 서비스
@Service
@RequiredArgsConstructor
public class UserDetailsServiceimpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl.loadUserByUsername : " + loginId);

        // 사용자 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserDetailsImpl 반환
        return new UserDetailsImpl(member, member.getLoginId(), member.getPassword());
    }
}
