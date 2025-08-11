package com.microservices.apigateway.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Slf4j
@Configuration
public class SecurityConfig {
    @Value("${keycloak.auth.jwk.set-uri}")
    private String jwkSetUri;

    @Value("${security.excluded.urls}")
    private String[] excludedUrls;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        log.info(Arrays.toString(excludedUrls));
        return httpSecurity.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(excludedUrls)
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}
