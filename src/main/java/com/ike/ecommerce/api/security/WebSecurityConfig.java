package com.ike.ecommerce.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@SuppressWarnings("deprecation")
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer :: disable)
                .cors(AbstractHttpConfigurer :: disable)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().permitAll()
                );
        return http.build();
    }
}
