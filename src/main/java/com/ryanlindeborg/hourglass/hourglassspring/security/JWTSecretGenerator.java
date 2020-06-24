package com.ryanlindeborg.hourglass.hourglassspring.security;

import com.ryanlindeborg.hourglass.hourglassspring.security.JwtService;

import java.util.Base64;


public class JWTSecretGenerator {

    public static void main(String[] args) {
        byte[] s = JwtService.generateJWTSecret();
        System.out.println(new String(Base64.getEncoder().encode(s)));
    }
}
