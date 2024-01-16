package com.brand13.authtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.brand13.authtest.handler.MyAccessDeniedHandler;
import com.brand13.authtest.handler.MyAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Autowired MyAccessDeniedHandler myAccessDeniedHandler;
    
    @Autowired MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    
    @Bean
    public BCryptPasswordEncoder encodePwd() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain fintChain(HttpSecurity http) throws Exception {

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
                .csrf(csrf -> 
                    csrf.disable())
                .formLogin(form -> 
                    form
                        .permitAll()
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
}
