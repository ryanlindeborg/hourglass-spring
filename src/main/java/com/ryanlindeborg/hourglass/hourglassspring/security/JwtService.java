package com.ryanlindeborg.hourglass.hourglassspring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtService {
    @Value("${com.ryanlindeborg.hourglass.jwt-secret}")
    private String secret;

    public void resetSecret() {
        secret = new String(generateJWTSecret());
    }

    public byte[] getJWTSecret() {
        return Base64.getDecoder().decode(secret);
    }

    public static byte[] generateJWTSecret() {
        SecureRandom rand = new SecureRandom();
        byte[] newSecretBytes = new byte[512];
        rand.nextBytes(newSecretBytes);
        return newSecretBytes;
    }
}
