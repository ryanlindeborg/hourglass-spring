package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.SimilarUser;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     * @param profileDetails
     * @return profileDetails that was passed into method back to front end so can edit if hit validation error
     */
    @Transactional
    public ProfileDetails saveProfileFromJson(ProfileDetails profileDetails) {
        try {
            //--------- Process user ---------//
            User user = profileDetails.getUser();
            User existingUser = null;
            // Check if user already exists
            if (user.getId() != null) {
                // Update existing user
                existingUser = userRepository.findById(user.getId()).orElse(null);
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setBirthDate(user.getBirthDate());
                user = existingUser;
            } else {
                // Save user as default user
                user.setUserType(UserType.DEFAULT);
            }
            userRepository.save(user);

            //--------- Process currentJob ---------//
            Job currentJob = profileDetails.getCurrentJob();
            Job existingCurrentJob = null;
            Company currentJobCompany = profileDetails.getCurrentJob().getCompany();

            // See if current job company is existing
            if (currentJobCompany.getId() != null) {
                currentJobCompany = companyRepository.findById(currentJobCompany.getId()).orElse(null);
            } else {
                // If company id was not already set, search for company existence
                String currentJobCompanyName = currentJob.getCompany().getName();
                Company existingCurrentJobCompany = companyRepository.getCompanyByName(currentJobCompanyName);
                if (existingCurrentJobCompany != null) {
                    currentJobCompany = existingCurrentJobCompany;
                } else {
                    // If company is not existing in database, save it as new company
                    currentJobCompany = currentJob.getCompany();
                    currentJobCompany = companyRepository.save(currentJobCompany);
                }
            }

            // Check if current job already exists
            if (currentJob.getId() != null) {
                // Update existing current job
                existingCurrentJob = jobRepository.findById(currentJob.getId()).orElse(null);
                existingCurrentJob.setPosition(currentJob.getPosition());
                existingCurrentJob.setIndustry(currentJob.getIndustry());
                existingCurrentJob.setCompany(currentJobCompany);
                currentJob = existingCurrentJob;
            } else {
                // Create new job of correct job type
                currentJob.setCompany(currentJobCompany);
                currentJob.setJobType(JobType.CURRENT_JOB);
                currentJob.setUser(user);
                currentJob.setCompany(currentJobCompany);
            }
            jobRepository.save(currentJob);

            //--------- Process firstPostCollegeJob ---------//
            Job firstPostCollegeJob = profileDetails.getFirstPostCollegeJob();
            Job existingFirstPostCollegeJob = null;
            Company firstPostCollegeJobCompany = profileDetails.getFirstPostCollegeJob().getCompany();

            // See if first post-college job company is existing
            if (firstPostCollegeJobCompany.getId() != null) {
                firstPostCollegeJobCompany = companyRepository.findById(firstPostCollegeJobCompany.getId()).orElse(null);
            } else {
                // If company id was not already set, search for company existence
                String firstPostCollegeJobCompanyName = firstPostCollegeJob.getCompany().getName();
                Company existingFirstPostCollegeJobCompany = companyRepository.getCompanyByName(firstPostCollegeJobCompanyName);
                if (existingFirstPostCollegeJobCompany != null) {
                    firstPostCollegeJobCompany = existingFirstPostCollegeJobCompany;
                } else {
                    // If company is not existing in database, save it as new company
                    firstPostCollegeJobCompany = firstPostCollegeJob.getCompany();
                    firstPostCollegeJobCompany = companyRepository.save(firstPostCollegeJobCompany);
                }
            }

            // Check if first post-college job already exists
            if (firstPostCollegeJob.getId() != null) {
                // Update existing first post-college job
                existingFirstPostCollegeJob = jobRepository.findById(firstPostCollegeJob.getId()).orElse(null);
                existingFirstPostCollegeJob.setPosition(firstPostCollegeJob.getPosition());
                existingFirstPostCollegeJob.setIndustry(firstPostCollegeJob.getIndustry());
                existingFirstPostCollegeJob.setCompany(firstPostCollegeJobCompany);
                firstPostCollegeJob = existingFirstPostCollegeJob;
            } else {
                // Create new job of correct job type
                firstPostCollegeJob.setCompany(firstPostCollegeJobCompany);
                firstPostCollegeJob.setJobType(JobType.FIRST_POST_COLLEGE_JOB);
                firstPostCollegeJob.setUser(user);
                firstPostCollegeJob.setCompany(firstPostCollegeJobCompany);
            }
            jobRepository.save(firstPostCollegeJob);

            //--------- Process dreamJob ---------//
            Job dreamJob = profileDetails.getDreamJob();
            Job existingDreamJob = null;
            Company dreamJobCompany = profileDetails.getDreamJob().getCompany();

            // See if dream job company is existing
            if (dreamJobCompany.getId() != null) {
                dreamJobCompany = companyRepository.findById(dreamJobCompany.getId()).orElse(null);
            } else {
                // If company id was not already set, search for company existence
                String dreamJobCompanyName = dreamJob.getCompany().getName();
                Company existingDreamJobCompany = companyRepository.getCompanyByName(dreamJobCompanyName);
                if (existingDreamJobCompany != null) {
                    dreamJobCompany = existingDreamJobCompany;
                } else {
                    // If company is not existing in database, save it as new company
                    dreamJobCompany = dreamJob.getCompany();
                    dreamJobCompany = companyRepository.save(dreamJobCompany);
                }
            }

            // Check if dream job already exists
            if (dreamJob.getId() != null) {
                // Update existing dream job
                existingDreamJob = jobRepository.findById(dreamJob.getId()).orElse(null);
                existingDreamJob.setPosition(dreamJob.getPosition());
                existingDreamJob.setIndustry(dreamJob.getIndustry());
                existingDreamJob.setCompany(dreamJobCompany);
                dreamJob = existingDreamJob;
            } else {
                // Create new job of correct job type
                dreamJob.setCompany(dreamJobCompany);
                dreamJob.setJobType(JobType.DREAM_JOB);
                dreamJob.setUser(user);
                dreamJob.setCompany(dreamJobCompany);
                jobRepository.save(dreamJob);
            }

            //--------- Process collegeSchoolUser ---------//
            //TODO: Create logic to process collegeSchoolUser
            // school, end date, field of study
            SchoolUser collegeSchoolUser = profileDetails.getCollegeSchoolUser();
            School college = profileDetails.getCollegeSchoolUser().getSchool();

            // See if school is existing
            if (college.getId() != null) {
                college = schoolRepository.findById(college.getId()).orElse(null);
            } else {
                // If school not already saved,
            }


            // See if college school user is existing
            if (collegeSchoolUser.getId() != null) {
                collegeSchoolUser = schoolUserRepository.findById(collegeSchoolUser.getId()).orElse(null);
            } else {

            }

            //--------- Process postGradSchoolUser ---------//
            // school, end date, degree, field of study

            return profileDetails;
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
