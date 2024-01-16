package com.brand13.authtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.brand13.authtest.handler.MyAccessDeniedHandler;
import com.brand13.authtest.handler.MyAuthenticationEntryPoint;

// @Configuration
// @EnableWebSecurity
public class SpringConfig_old {
    
    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired MyAccessDeniedHandler myAccessDeniedHandler;
    
    @Autowired MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder encodePwd() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            
        return http.authorizeHttpRequests(auth -> 
                        auth
                            // .requestMatchers("/api/admin").hasAuthority("ROLE_ADMIN")
                            // .requestMatchers("/api/admin").hasAuthority("ROLE_USER") // localhost:8080/api/admin 접속시 403 권한에러가 발생한다. LoginUserDetails에서 getAuthority가 DB의 role 필드에 따라 부여되기 때문
                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            // .requestMatchers("/login").permitAll()
                            // .requestMatchers("/loginForm").permitAll()
                            .requestMatchers("/logout").permitAll()
                            .requestMatchers("/api/hello").permitAll()
                            .requestMatchers("/api/authorization-fail").permitAll()
                            .requestMatchers("/api/authentication-fail").permitAll()
                            .anyRequest().authenticated()
                    )
                    .exceptionHandling(exceptionHandlingCustomizer -> 
                                            exceptionHandlingCustomizer
                                                .authenticationEntryPoint(myAuthenticationEntryPoint)
                                                .accessDeniedHandler(myAccessDeniedHandler)
                    )
                    .csrf(csrf ->
                        // csrf.ignoringRequestMatchers("/h2-console/**")
                        csrf.disable()
                    )
                    .headers(header ->
                        // header.frameOptions(frameOption -> frameOption.disable())
                        header.frameOptions(frameOption -> frameOption.sameOrigin())
                    )   
                    .formLogin(form -> 
                        form
                            // .loginProcessingUrl("/login")
                            // .permitAll()
                            // .loginPage("/login") //GET
                            // .loginProcessingUrl("/api/login") //POST : form data로 보낼 것, 따로 contorller를 만들지 않아도 된다.
                            .defaultSuccessUrl("/api/hello", true)
                            .failureUrl("/api/hello")
                            .permitAll()
                    )
                    .logout(logoutCustomizer -> 
                                logoutCustomizer
                                .logoutUrl("/logout")
                                // .logoutSuccessUrl("/login")
                    ).build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
