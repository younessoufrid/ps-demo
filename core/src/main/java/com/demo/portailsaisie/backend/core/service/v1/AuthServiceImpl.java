package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.dto.auth.AuthRequestDto;
import com.demo.portailsaisie.backend.core.dto.auth.AuthResponseDto;
import com.demo.portailsaisie.backend.core.dto.auth.ImmutableAuthResponseDto;
import com.demo.portailsaisie.backend.core.dto.auth.KeycloakTokenResponseDto;
import com.demo.portailsaisie.backend.core.dto.user.ImmutableUserRequestDto;
import com.demo.portailsaisie.backend.core.mapping.AuthMapper;
import com.demo.portailsaisie.backend.core.service.interfaces.AuthService;
import com.demo.portailsaisie.backend.core.service.interfaces.UserService;
import com.demo.portailsaisie.backend.utils.Constants;
import com.demo.portailsaisie.backend.utils.security.JwtUtils;
import com.demo.portailsaisie.backend.utils.security.KeycloakJwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.jwt-auth-url}")
    private String keycloakAuthUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserService userService;
    private final AuthMapper mapper;
    private final KeycloakJwtService keycloakJwtService;

    @Override
    public AuthResponseDto authenticateWithKeycloak(AuthRequestDto authRequestDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("grant_type", "password");
        map.add("username", authRequestDto.getUsername());
        map.add("password", authRequestDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                keycloakAuthUrl,
                request,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            KeycloakTokenResponseDto responseDto = mapper.mapToKeycloakDto(response.getBody());
            String username = keycloakJwtService.extractUsername(responseDto.getAccess_token());
            Set<String> roles = keycloakJwtService.extractRoles(responseDto.getAccess_token());

            return ImmutableAuthResponseDto
                    .builder()
                    .token(JwtUtils.createJWTToken(username, roles.stream().toList(), Constants.JWT_SOURCE_OIDC))
                    .username(username)
                    .build();
        } else {
            throw new RuntimeException("Failed to obtain access token from Keycloak");
        }
    }

}
