package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileJson that = (ProfileJson) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(jobs, that.jobs) &&
                Objects.equals(schoolUsers, that.schoolUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, jobs, schoolUsers);
    }
}
