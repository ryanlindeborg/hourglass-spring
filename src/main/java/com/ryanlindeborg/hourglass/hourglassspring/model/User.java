package com.ryanlindeborg.hourglass.hourglassspring.model;

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
    @Column(name = "image_square_name")
    private String imageSquareName;
    @Column(name = "image_rectangle_name")
    private String imageRectangleName;

    //TODO: How would this be accomplished?
    public Job getCurrentJob() {
        return null;
    }
}
