package com.demo.portailsaisie.backend.utils.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

import static com.demo.portailsaisie.backend.utils.Constants.*;

@Component
public class KeycloakJwtService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakJwtService.class);

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private static PublicKey keycloakPublicKey;

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getKeycloakPublicKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get(KeycloakJwtFormat.PREFERRED_USERNAME.getValue(), String.class);
    }

    public Set<String> extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
        return extractRealmRoles(claims);
    }

    private Set<String> extractRealmRoles(Claims claims) {
        final Map<String, List<String>> realmAccess =
                (Map<String, List<String>>) claims.get(KeycloakJwtFormat.REALM_ACCESS.getValue());
        final List<String> keycloakRoles = realmAccess.get("roles");
        final List<String> backendRoles = Arrays.stream(AccessRoles.values()).map(Enum::name).toList();

        if (CollectionUtils.isNotEmpty(keycloakRoles)) {
            return keycloakRoles.stream()
                    .map(String::toUpperCase)
                    .filter(backendRoles::contains)
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    private PublicKey getKeycloakPublicKey(){
        if(keycloakPublicKey != null)
            return keycloakPublicKey;

        String url = keycloakUrl + "/realms/" + realm;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            String publicKey = jsonNode.get(KEYCLOAK_PUBLIC_KEY).asText();
            keycloakPublicKey = JwtUtils.getGenericPublicKey(publicKey);
            return keycloakPublicKey;
        } catch (JsonProcessingException e) {
            logger.error("Exception while getting keycloak public key: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
