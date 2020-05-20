package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the profile details that client sends from the MyCareer Form
 */
@Data
@NoArgsConstructor
public class ProfileDetails {
    private User user;
    private Job currentJob;
    private Job firstPostCollegeJob;
    private Job dreamJob;
    private SchoolUser collegeSchoolUser;
    private SchoolUser postGradSchoolUser;
}
