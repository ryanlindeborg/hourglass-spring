package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolJson {
    private Long id;
    private String name;

    public School createSchool() {
        School school = School.builder()
                .id(id)
                .name(name)
                .build();

        return school;
    }

    public static SchoolJson createEmptySchoolJson() {
        return new SchoolJson().builder().build();
    }
}
