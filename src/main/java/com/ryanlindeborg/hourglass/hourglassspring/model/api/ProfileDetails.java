package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.JobJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.SchoolUserJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.UserJson;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the profile details that client sends from the MyCareer Form
 */
@Data
@NoArgsConstructor
public class ProfileDetails {
    private UserJson userJson;
    private JobJson currentJobJson;
    private JobJson firstPostCollegeJobJson;
    private JobJson dreamJobJson;
    private SchoolUserJson collegeSchoolUserJson;
    private SchoolUserJson postGradSchoolUserJson;
}
