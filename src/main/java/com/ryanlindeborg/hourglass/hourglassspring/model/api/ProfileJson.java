package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import lombok.*;

import java.util.List;

/**
 * Class representing json that will be sent to client for user profile
 *
 */

@Data
@NoArgsConstructor
public class ProfileJson {
    private User user;
    private List<Job> jobs;
    private List<SchoolUser> schoolUsers;
}
