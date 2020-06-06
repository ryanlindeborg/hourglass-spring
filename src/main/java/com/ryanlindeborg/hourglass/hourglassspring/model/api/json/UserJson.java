package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public User createUser() {
        User user = User.builder()
                .id(id)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .birthDate(birthDate)
                .userType(userType)
                .username(username)
                .displayName(displayName)
                .imageSquareName(imageSquareName)
                .imageRectangleName(imageRectangleName)
                .build();

        return user;
    }
}
