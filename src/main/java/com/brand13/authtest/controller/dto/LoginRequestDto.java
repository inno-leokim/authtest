package com.brand13.authtest.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
    private String role;
    
    
    @Builder
    public LoginRequestDto(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role     = role;
    }
    
    
}
