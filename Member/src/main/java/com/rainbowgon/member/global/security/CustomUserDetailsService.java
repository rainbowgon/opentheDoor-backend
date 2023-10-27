package com.rainbowgon.member.global.security;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.global.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.debug("[CustomUserDetailsService] loadUserByUsername 로직 start");
        
        Member member = memberRepository.findById(UUID.fromString(memberId)).orElseThrow(MemberNotFoundException::new);

        return CustomUserDetails.builder()
                .memberId(member.getId().toString())
                .build();
    }
}
