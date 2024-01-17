package com.brand13.authtest.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.brand13.authtest.controller.dto.StatusResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        sendSuccessResponse(response);
    }
    
    private void sendSuccessResponse(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        StatusResponseDto successDto = StatusResponseDto.builder()
                                        .statusCode(HttpStatus.OK.value())
                                        .statusMsg("Login Success")
                                        .msg("인증에 성공했습니다.")
                                        .build();

        String result = objectMapper.writeValueAsString(successDto);
        response.getWriter().write(result);
        
    }
}
