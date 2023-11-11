package com.rainbowgon.memberservice.global.security.filter;

import com.rainbowgon.memberservice.global.error.exception.AuthTokenExpiredException;
import com.rainbowgon.memberservice.global.security.CustomUserDetailsService;
import com.rainbowgon.memberservice.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getToken(request);

        if (accessToken != null) {
            String memberId = tokenProvider.getProfileId(accessToken); // 토큰에 담긴 회원 id 정보
            UserDetails authentication = customUserDetailsService.loadUserByUsername(memberId); // 토큰에 담긴
            // 정보로 불러온 회원 정보

            if (tokenProvider.validateToken(accessToken)) { // 유효한 토큰인지(만료 여부) 확인
                log.info("[JwtFilter] 만료된 토큰");
                throw AuthTokenExpiredException.EXCEPTION;
            }
            UsernamePasswordAuthenticationToken auth
                    = new UsernamePasswordAuthenticationToken(authentication.getUsername(), null, null);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        String headerAuth = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
