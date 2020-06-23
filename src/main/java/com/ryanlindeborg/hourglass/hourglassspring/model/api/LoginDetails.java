package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDetails {
    // This can be either the username or the email of the user
    private String loginName;
    private String password;
}
