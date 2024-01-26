package com.brand13.authtest.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.brand13.authtest.handler.LoginSuccessHandler;
import com.brand13.authtest.handler.MyAccessDeniedHandler;
import com.brand13.authtest.handler.MyAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Autowired MyAccessDeniedHandler myAccessDeniedHandler; // 인가 실패 후 작업에 대한 핸들러
    
    @Autowired MyAuthenticationEntryPoint myAuthenticationEntryPoint; //인증 실패 후 작업에 대한 핸들러

    @Autowired LoginSuccessHandler loginSuccessHandler; //인증 성공 후 작업에 대한 핸들러
    
    @Bean
    public BCryptPasswordEncoder encodePwd() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(
                    auth ->
                        auth
                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            .requestMatchers("/api/admin").hasAuthority("ROLE_USER")
                            .requestMatchers("/api/logout").permitAll()
                            .requestMatchers("/login").permitAll()
                            .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandlingCustomizer -> 
                                            exceptionHandlingCustomizer
                                                .authenticationEntryPoint(myAuthenticationEntryPoint)
                                                .accessDeniedHandler(myAccessDeniedHandler)
                )
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> 
                    csrf.disable())
                .formLogin(form -> 
                    form
                        .successHandler(loginSuccessHandler)
                        // .permitAll()
                )
                .logout(logoutCustomizer -> 
                    logoutCustomizer
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/api/logout")
                )
                .headers(header -> 
                    header.frameOptions(frameOption -> frameOption.sameOrigin())
                ).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:8000"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L); //1시간
                return config;
            }
        };   
    }

    
}
