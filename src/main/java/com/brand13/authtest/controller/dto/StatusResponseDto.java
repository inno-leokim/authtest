package com.brand13.authtest.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusResponseDto {
    private int statusCode;
    private String statusMsg;
    private String msg;
    
    @Builder
    public StatusResponseDto(int statusCode, String statusMsg, String msg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.msg = msg;
    }
}
