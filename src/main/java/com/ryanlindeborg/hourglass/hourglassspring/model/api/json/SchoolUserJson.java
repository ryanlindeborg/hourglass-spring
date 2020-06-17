package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.Degree;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolUserJson {
    private Long id;
    private UserJson userJson;
    private SchoolJson schoolJson;
    private Date startDate;
    private Date endDate;
    private Degree degree;
    private String fieldOfStudy;
    private Boolean isCompleted;
    private SchoolUserType schoolUserType;

    public SchoolUser createSchoolUser() {
        SchoolUser schoolUser = SchoolUser.builder()
                .id(id)
                .user(userJson.createUser())
                .school(schoolJson.createSchool())
                .startDate(startDate)
                .endDate(endDate)
                .degree(degree)
                .fieldOfStudy(fieldOfStudy)
                .isCompleted(isCompleted)
                .schoolUserType(schoolUserType)
                .build();

        return schoolUser;
    }
}