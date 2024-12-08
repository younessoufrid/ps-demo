package com.demo.portailsaisie.backend.security.filters;


import com.demo.portailsaisie.backend.utils.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.Optional;

import static com.demo.portailsaisie.backend.utils.Constants.JWT_SOURCE_OIDC;
import static com.demo.portailsaisie.backend.utils.Constants.ROLE_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
           Optional<String> token = JwtUtils.getTokenWithoutBearer(request);
           token.ifPresent(this::verifyAuthenticationOfUser);
           filterChain.doFilter(request, response);
    }
    private void verifyAuthenticationOfUser(String token) {
        if (JwtUtils.verifyToken(token)) {
            String username = JwtUtils.extractUsername(token);
            String source = JwtUtils.extractSource(token);
            if (username != null) {
                if (JWT_SOURCE_OIDC.equals(source)){
                    List<String> roles = JwtUtils.extractRoles(token);
                    UsernamePasswordAuthenticationToken authToken =
                            UsernamePasswordAuthenticationToken.authenticated(
                                    username,
                                    null,
                                    roles.stream()
                                            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX.concat(role)))
                                            .toList()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
    }
}
