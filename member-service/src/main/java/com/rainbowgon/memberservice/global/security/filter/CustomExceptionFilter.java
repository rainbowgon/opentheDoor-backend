package com.rainbowgon.memberservice.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rainbowgon.memberservice.global.error.dto.ErrorReason;
import com.rainbowgon.memberservice.global.error.dto.ErrorResponse;
import com.rainbowgon.memberservice.global.error.errorCode.BaseErrorCode;
import com.rainbowgon.memberservice.global.error.exception.AuthTokenExpiredException;
import com.rainbowgon.memberservice.global.error.exception.CustomException;
import com.rainbowgon.memberservice.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
public class CustomExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setErrorResponse(e, request.getRequestURL().toString(), response);
        }
    }

    private void setErrorResponse(CustomException customException, String requestUrl, HttpServletResponse response) {

        BaseErrorCode code = customException.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        response.setStatus(errorReason.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ErrorResponse errorResponse = ErrorResponse.of(errorReason, requestUrl);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
