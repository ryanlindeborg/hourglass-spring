package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
public class UserJson {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private UserType userType;
    private String username;
    private String displayName;
    private String imageSquareName;
    private String imageRectangleName;


}
