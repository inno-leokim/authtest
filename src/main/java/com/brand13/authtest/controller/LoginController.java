package com.brand13.authtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brand13.authtest.auth.LoginUserDetailsService;

@RequestMapping("/api")
@RestController
public class LoginController {
    
    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 테스트용
    @GetMapping("/hello")
    public void hello() {
        System.out.println("HelloWorld");
    }

    @GetMapping("/admin")
    public String admin(){
        System.out.println("HelloWorld");
        return "admin";
    }
}
