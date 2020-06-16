package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String displayName;
    private String password;
    private String repeatPassword;
}
