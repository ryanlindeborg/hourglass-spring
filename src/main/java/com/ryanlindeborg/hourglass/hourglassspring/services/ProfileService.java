package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private JobRepository jobRepository;
    private SchoolRepository schoolRepository;
    private SchoolUserRepository schoolUserRepository;

    public ProfileService(UserRepository userRepository, CompanyRepository companyRepository, JobRepository jobRepository, SchoolRepository schoolRepository, SchoolUserRepository schoolUserRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.schoolRepository = schoolRepository;
        this.schoolUserRepository = schoolUserRepository;
    }

    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    public ProfileJson getProfileJsonByUserId(Long userId) {
        ProfileJson profileJson = new ProfileJson();

        // Grab user
        profileJson.setUser(userRepository.findById(userId).get());

        // Grab list of jobs associated with user
        List<Job> jobs = jobRepository.getJobsByUserIdEquals(userId);
        profileJson.setJobs(jobs);

        // Grab list of school-user records associated with that user
        List<SchoolUser> schoolUsers = schoolUserRepository.getSchoolUsersByUserId(userId);
        profileJson.setSchoolUsers(schoolUsers);

        return profileJson;
    }
}