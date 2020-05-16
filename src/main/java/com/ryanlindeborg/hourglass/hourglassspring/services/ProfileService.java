package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.SimilarUser;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private JobRepository jobRepository;
    private SchoolRepository schoolRepository;
    private SchoolUserRepository schoolUserRepository;

    @Autowired
    public ProfileService(UserRepository userRepository, CompanyRepository companyRepository, JobRepository jobRepository, SchoolRepository schoolRepository, SchoolUserRepository schoolUserRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.schoolRepository = schoolRepository;
        this.schoolUserRepository = schoolUserRepository;
    }

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    public ProfileJson getProfileJsonByUserId(Long userId) {
        ProfileJson profileJson = new ProfileJson();

        // Grab user
        User user = getUserById(userId);
        if (user == null) {
            throw new HourglassRestException("User profile does not exist", HourglassRestErrorCode.RESOURCE_NOT_FOUND);
        }
        profileJson.setUser(user);

        // Grab list of jobs associated with user
        List<Job> jobs = jobRepository.getJobsByUserId(userId);
        profileJson.setJobs(jobs);

        // Grab list of school-user records associated with that user
        List<SchoolUser> schoolUsers = schoolUserRepository.getSchoolUsersByUserId(userId);
        profileJson.setSchoolUsers(schoolUsers);

        return profileJson;
    }

    /**
     *
     * @param profileDetailsJson
     * @return profileDetailsJson that was passed into method back to front end so can edit if hit validation error
     */
    public String saveProfileFromJson(String profileDetailsJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(profileDetailsJson);
            System.out.println("Json node: " + jsonNode);

            User user =  objectMapper.treeToValue(jsonNode.get("user"), User.class);
            Job job = objectMapper.treeToValue(jsonNode.get("job"), Job.class);
            job.setUser(user);
            jobRepository.save(job);
            Job firstPostCollegeJob = objectMapper.treeToValue(jsonNode.get("firstPostCollegeJob"), Job.class);
            firstPostCollegeJob.setUser(user);
            jobRepository.save(firstPostCollegeJob);
            Job dreamJob = objectMapper.treeToValue(jsonNode.get("dreamJob"), Job.class);
            dreamJob.setUser(user);
            jobRepository.save(dreamJob);
            SchoolUser collegeSchoolUser = objectMapper.treeToValue(jsonNode.get("collegeSchoolUser"), SchoolUser.class);
            collegeSchoolUser.setUser(user);
            schoolUserRepository.save(collegeSchoolUser);
            SchoolUser postGradSchoolUser = objectMapper.treeToValue(jsonNode.get("postGradSchoolUser"), SchoolUser.class);
            postGradSchoolUser.setUser(user);
            schoolUserRepository.save(postGradSchoolUser);

            return profileDetailsJson;
        } catch (Exception e) {
            throw new HourglassRestException(e.getMessage(), HourglassRestErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param userId
     * @return List of similar users that match user profile on at least one attribute
     *
     * {
     * "similarUsers": [
     *     {}
     *   ]
     *
     * }
     *
     */
    public List<SimilarUser> getSimilarUsers(Long userId) {
        // Should return list of users that match on at least one attribute, and in that list, for each user, have list of attributes that are similar on, and what field in object that they match on
        // SimilarAttribute object can be name of object and field similar on
        List<SimilarUser> similarUsers = new ArrayList<>();


        return null;
    }
}
