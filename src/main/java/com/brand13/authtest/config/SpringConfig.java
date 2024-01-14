package com.brand13.authtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {
    
    @Bean
    public BCryptPasswordEncoder encodePwd() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            
        return http.authorizeHttpRequests(auth -> 
                        auth
                            .requestMatchers("/h2-console/**").permitAll()
                            .anyRequest().permitAll()   
                    )
                    .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/h2-console/**")
                    )
                    .headers(header ->
                        // header.frameOptions(frameOption -> frameOption.disable())
                        header.frameOptions(frameOption -> frameOption.sameOrigin())
                    )   
                    .formLogin(form -> 
                        form
                            .permitAll()
                            .defaultSuccessUrl("/", true)
                    ).build();
    }
}
