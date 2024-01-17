package com.brand13.authtest.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.brand13.authtest.controller.dto.StatusResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                // response.setStatus(401);
        // response.sendRedirect("/api/authentication-fail");
        sendErrorResponse(response);        
    }
    
    private void sendErrorResponse(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        StatusResponseDto unauthorizeDto = StatusResponseDto.builder()
                                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                                            .statusMsg("Unauthouized")
                                            .msg("인증에 실패하였습니다.")
                                            .build();

        String result = objectMapper.writeValueAsString(unauthorizeDto);
        response.getWriter().write(result);

    }
}
