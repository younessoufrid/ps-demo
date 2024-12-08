package com.demo.portailsaisie.backend.security.config;

import com.demo.portailsaisie.backend.security.filters.JwtFilter;
import com.demo.portailsaisie.backend.utils.security.JwtUtils;
import com.demo.portailsaisie.backend.utils.security.KeyUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.timeout}")
    private int expiration;

    @Value("${jwt.public-key.location}")
    private String publicKeyLocation;

    @Value("${jwt.private-key.location}")
    private String privateKeyLocation;

    private final JwtFilter jwtFilter;

    private static final String[] AUTH_WHITELIST = {
            "/api/auth/ldap",
            "/api/auth/oidc",
            "/api/change_lang"
    };

    @PostConstruct
    private void initSecurityParams() {
        JwtUtils.expiration = expiration;
        KeyUtils.publicKeyLocation = publicKeyLocation;
        KeyUtils.privateKeyLocation = privateKeyLocation;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(AUTH_WHITELIST)
                                    .permitAll()
                                .anyRequest()
                                    .authenticated()
                )
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        security.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        return security.build();
    }

}
