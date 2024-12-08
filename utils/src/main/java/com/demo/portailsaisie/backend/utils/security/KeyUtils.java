package com.demo.portailsaisie.backend.utils.security;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.demo.portailsaisie.backend.utils.Constants.*;


public class KeyUtils {

    public static String publicKeyLocation;

    public static String privateKeyLocation;

    public static String getPublicKeyAsString() throws IOException {
        return getPublicKey(new ClassPathResource(publicKeyLocation));
    }

    private static String getPublicKey(ClassPathResource classPathResource) throws IOException {
        String publicKeyContent = new String(classPathResource.getInputStream().readAllBytes());
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace(PUBLIC_KEY_START, "").replace(PUBLIC_KEY_END, "");
        return publicKeyContent;
    }

    public static String getPrivateKeyAsBase64() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(privateKeyLocation);
        String privateKeyContent = new String(classPathResource.getInputStream().readAllBytes());
        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace(PRIVATE_KEY_START, "").replace(PRIVATE_KEY_END, "");
        return privateKeyContent;
    }
}
