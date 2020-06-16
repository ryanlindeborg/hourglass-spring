package com.ryanlindeborg.hourglass.hourglassspring.model.api.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationToken {
    private String token;
}
