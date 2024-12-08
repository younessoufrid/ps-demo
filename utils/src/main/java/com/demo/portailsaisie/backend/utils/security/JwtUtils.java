package com.demo.portailsaisie.backend.utils.security;

import com.demo.portailsaisie.backend.utils.Constants;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.*;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final SignatureAlgorithm RS256 = SignatureAlgorithm.RS256;
    private static final String ALG_NAME = RS256.getFamilyName();
    private static final int BEARER_INDEX = Constants.HEADER_AUTHORIZATION_PREFIX.length();
    private static KeyFactory factory;
    public static int expiration;


    static {
        try {
            factory = KeyFactory.getInstance(ALG_NAME);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error creating KeyFactory", e);
        }
    }

    private JwtUtils() {

    }

    public static String createJWTToken(String username, List<String> grantedAuthorities, String source) {
        ZonedDateTime now = ZonedDateTime.now();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.plusSeconds(expiration).toInstant()))
                .signWith(RS256, getPrivateKey())
                .claim(Constants.JWT_CLAIM_USER_ROLES, grantedAuthorities)
                .claim(Constants.JWT_SOURCE_CLAIM, source) // TO DELETE AFTER DELETE OF LDAP AUTH
                .compact();
    }

    public static Optional<String> getTokenWithoutBearer(HttpServletRequest request) {
        Optional<String> token = Optional.ofNullable(request.getHeader(Constants.HEADER_AUTHORIZATION));
        return getTokenWithoutBearer(token);
    }

    public static Optional<String> getTokenWithoutBearer(Optional<String> tokenWithBearerPrefix) {
        return tokenWithBearerPrefix.map(s -> StringUtils.substring(s, BEARER_INDEX));
    }

    public static boolean verifyToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: %s",  e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: %s", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: %s", e.getMessage());
            throw e;
        }
    }

    public static String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public static String extractSource(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get(Constants.JWT_SOURCE_CLAIM, String.class);
    }

    public static ArrayList<String> extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get(Constants.JWT_CLAIM_USER_ROLES, ArrayList.class);
    }

    public static Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
    }


    public static PublicKey getPublicKey() {
        try {
            return getGenericPublicKey(KeyUtils.getPublicKeyAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey getGenericPublicKey(String publicKeyContent) {
        try {
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyContent));
            return factory.generatePublic(keySpecX509);
        } catch (Exception e) {
            logger.error("Exception while generating public key: {}", e.getMessage());
        }
        return null;
    }

    public static PrivateKey getPrivateKey() {
        try {
            String privateKeyContent = KeyUtils.getPrivateKeyAsBase64();
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyContent));
            return factory.generatePrivate(keySpecPKCS8);
        } catch (Exception e) {
            logger.error("Exception while generating public key: {}", e.getMessage());
        }
        return null;
    }
}
