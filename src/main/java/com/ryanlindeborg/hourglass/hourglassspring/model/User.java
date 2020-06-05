package com.ryanlindeborg.hourglass.hourglassspring.model;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.UserJson;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @NotBlank(message="First name is required")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    @NotBlank(message="Last name is required")
    private String lastName;
    @Column(name = "birth_date")
    @NotNull(message="Birth date is required")
    private Date birthDate;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
    // Must be unique across users
    @Column(name = "username")
    @NotBlank(message="Username is required")
    private String username;
    // Must be unique across users
    @Column(name = "displayName")
    @NotBlank(message="Profile display name is required")
    private String displayName;
    // Aspect ratio 1x1 wxh
    @Column(name = "image_square_name")
    private String imageSquareName;
    // Aspect ratio 3x4 wxh
    @Column(name = "image_rectangle_name")
    private String imageRectangleName;

    public UserJson createUserJson() {
        UserJson userJson = UserJson.builder()
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

        return userJson;
    }
}
