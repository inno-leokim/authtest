package com.brand13.authtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.brand13.authtest.auth.LoginUserDetailsService;

@RequestMapping("/api")
@RestController
public class LoginController {
    
    // @Autowired
    // private LoginUserDetailsService loginUserDetailsService;

    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/authentication-fail")
    public String authFail(){
        System.out.println("HelloWorld");
        return "authentication fail";
    }

    @GetMapping("/authorization-fail")
    public String authorFail(){
        System.out.println("HelloWorld");
        return "authentication fail";
    }

    @GetMapping("/logout")
    public String logout(){
        return "Logout success";
    }

}
