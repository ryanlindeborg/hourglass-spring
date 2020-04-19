package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;

import java.util.List;

/**
 * Class representing json that will be sent to client for user profile
 *
 */

public class ProfileJson {
    private User user;
    private List<Job> jobs;
    private List<SchoolUser> schoolUsers;

    public ProfileJson() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<SchoolUser> getSchoolUsers() {
        return schoolUsers;
    }

    public void setSchoolUsers(List<SchoolUser> schoolUsers) {
        this.schoolUsers = schoolUsers;
    }
}
