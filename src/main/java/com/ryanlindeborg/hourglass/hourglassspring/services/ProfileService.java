package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.*;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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

    public List<ProfilePreview> getProfilePreviews() {
        //TODO: Update this to take a range maybe for pagination
        List<User> users = userRepository.findAll();
        List<ProfilePreview> profilePreviews = new ArrayList<>();

        for (User user : users) {
            ProfilePreview profilePreview = new ProfilePreview();
            profilePreview.setUserJson(user.createUserJson());

            Job currentJob = jobRepository.getCurrentJobForUserByUserId(user.getId());
            if (currentJob != null) {
                profilePreview.setCurrentJobJson(currentJob.createJobJson());
            }

            profilePreviews.add(profilePreview);
        }

        return profilePreviews;
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

    public ProfileJson getProfileJsonByUserDisplayName(String displayName) {
        ProfileJson profileJson = new ProfileJson();

        // Grab user
        User user = userRepository.getUserByDisplayName(displayName);
        if (user == null) {
            throw new HourglassRestException("User profile does not exist", HourglassRestErrorCode.RESOURCE_NOT_FOUND);
        }
        profileJson.setUser(user);

        // Grab list of jobs associated with user
        List<Job> jobs = jobRepository.getJobsByUserId(user.getId());
        profileJson.setJobs(jobs);

        // Grab list of school-user records associated with that user
        List<SchoolUser> schoolUsers = schoolUserRepository.getSchoolUsersByUserId(user.getId());
        profileJson.setSchoolUsers(schoolUsers);

        return profileJson;
    }



    /**
     *
     * This method generates the components of a user's profile
     *
     * @param userId
     * @return profileDetails tied to user
     */
//    public ProfileDetails getProfileDetailsByUserId(Long userId) {
//        ProfileDetails profileDetails = new ProfileDetails();
//        User user = userRepository.findById(userId).orElse(null);
//        if (user == null) {
//            throw new HourglassRestException("Unable to find this user",  HourglassRestErrorCode.RESOURCE_NOT_FOUND);
//        }
//        profileDetails.setUser(user);
//
//        Job currentJob = jobRepository.getJobByUserIdAndJobType(user.getId(), JobType.CURRENT_JOB);
//        profileDetails.setCurrentJob(currentJob);
//
//        Job firstPostCollegeJob = jobRepository.getJobByUserIdAndJobType(user.getId(), JobType.FIRST_POST_COLLEGE_JOB);
//        profileDetails.setFirstPostCollegeJob(firstPostCollegeJob);
//
//        Job dreamJob = jobRepository.getJobByUserIdAndJobType(user.getId(), JobType.DREAM_JOB);
//        profileDetails.setDreamJob(dreamJob);
//
//        SchoolUser collegeSchoolUser = schoolUserRepository.getSchoolUserByUserIdAndSchoolUserType(user.getId(), SchoolUserType.COLLEGE);
//        profileDetails.setCollegeSchoolUser(collegeSchoolUser);
//
//        SchoolUser postGradSchoolUser = schoolUserRepository.getSchoolUserByUserIdAndSchoolUserType(user.getId(), SchoolUserType.POST_GRAD);
//        profileDetails.setPostGradSchoolUser(postGradSchoolUser);
//
//        return profileDetails;
//    }

    /**
     *
     * This method generates the components of a user's profile
     *
     * @param displayName
     * @return profileDetails tied to user
     */
    public ProfileDetails getProfileDetailsByUserDisplayName(String displayName) {
        ProfileDetails profileDetails = new ProfileDetails();

        User user = userRepository.getUserByDisplayName(displayName);
        if (user == null) {
            throw new HourglassRestException("Unable to find this user",  HourglassRestErrorCode.RESOURCE_NOT_FOUND);
        }
        UserJson userJson = user.createUserJson();
        profileDetails.setUserJson(userJson);

        Job currentJob = jobRepository.getCurrentJobForUserByUserId(user.getId());
        if (currentJob != null) {
            JobJson currentJobJson = currentJob.createJobJson();
            profileDetails.setCurrentJobJson(currentJobJson);
        } else {
            profileDetails.setCurrentJobJson(JobJson.createEmptyJobJson());
        }

        Job firstPostCollegeJob = jobRepository.getFirstPostCollegeJobForUserByUserId(user.getId());
        if (firstPostCollegeJob != null) {
            JobJson firstPostCollegeJobJson = firstPostCollegeJob.createJobJson();
            profileDetails.setFirstPostCollegeJobJson(firstPostCollegeJobJson);
        } else {
            profileDetails.setFirstPostCollegeJobJson(JobJson.createEmptyJobJson());
        }

        Job dreamJob = jobRepository.getDreamJobForUserByUserId(user.getId());
        if (dreamJob != null) {
            JobJson dreamJobJson = dreamJob.createJobJson();
            profileDetails.setDreamJobJson(dreamJobJson);
        } else {
            profileDetails.setDreamJobJson(JobJson.createEmptyJobJson());
        }

        SchoolUser collegeSchoolUser = schoolUserRepository.getCollegeSchoolUserByUserId(user.getId());
        if (collegeSchoolUser != null) {
            SchoolUserJson collegeSchoolUserJson = collegeSchoolUser.createSchoolUserJson();
            profileDetails.setCollegeSchoolUserJson(collegeSchoolUserJson);
        } else {
            profileDetails.setCollegeSchoolUserJson(SchoolUserJson.createEmptySchoolUserJson());
        }

        SchoolUser postGradSchoolUser = schoolUserRepository.getPostGradSchoolUserByUserId(user.getId());
        if (postGradSchoolUser != null) {
            SchoolUserJson postGradSchoolUserJson = postGradSchoolUser.createSchoolUserJson();
            profileDetails.setPostGradSchoolUserJson(postGradSchoolUserJson);
        } else {
            profileDetails.setPostGradSchoolUserJson(SchoolUserJson.createEmptySchoolUserJson());
        }

        return profileDetails;
    }

    /**
     *
     * @param profileDetails
     * @return profileDetails that was passed into method back to front end so can edit if hit validation error
     */
    @Transactional
    public ProfileDetails saveProfileForUser(ProfileDetails profileDetails, String userDisplayName) {
        try {
            //--------- Process user ---------//
            User user = userRepository.getUserByDisplayName(userDisplayName);
            if (user == null) {
                throw new HourglassRestException().builder()
                        .message("Looks like we can't find your user profile."
                                + " Please reach out to customer support to fix this.")
                        .build();
            }

            //--------- Process currentJob ---------//
            CompanyJson currentJobCompanyJson = profileDetails.getCurrentJobJson().getCompanyJson();
            Company currentJobCompany = getCompanyFromCompanyJson(currentJobCompanyJson);

            Job currentJob = saveJobFromJobJson(profileDetails.getCurrentJobJson(), user, currentJobCompany);
            currentJob.setJobType(JobType.CURRENT_JOB);
            jobRepository.save(currentJob);

            //--------- Process firstPostCollegeJob ---------//
            CompanyJson firstPostCollegeJobCompanyJson = profileDetails.getFirstPostCollegeJobJson().getCompanyJson();
            Company firstPostCollegeJobCompany = getCompanyFromCompanyJson(firstPostCollegeJobCompanyJson);

            Job firstPostCollegeJob = saveJobFromJobJson(profileDetails.getFirstPostCollegeJobJson(), user, firstPostCollegeJobCompany);
            firstPostCollegeJob.setJobType(JobType.FIRST_POST_COLLEGE_JOB);
            jobRepository.save(firstPostCollegeJob);

            //--------- Process dreamJob ---------//
            CompanyJson dreamJobCompanyJson = profileDetails.getDreamJobJson().getCompanyJson();
            Company dreamJobCompany = getCompanyFromCompanyJson(dreamJobCompanyJson);

            Job dreamJob = saveJobFromJobJson(profileDetails.getDreamJobJson(), user, dreamJobCompany);
            dreamJob.setJobType(JobType.DREAM_JOB);
            jobRepository.save(dreamJob);

            //--------- Process collegeSchoolUser ---------//
            SchoolJson collegeJson = profileDetails.getCollegeSchoolUserJson().getSchoolJson();
            School college = getSchoolFromSchoolJson(collegeJson);

            SchoolUser collegeSchoolUser = saveSchoolUserFromSchoolUserJson(profileDetails.getCollegeSchoolUserJson(), user, college);
            collegeSchoolUser.setSchoolUserType(SchoolUserType.COLLEGE);
            schoolUserRepository.save(collegeSchoolUser);

            //--------- Process postGradSchoolUser ---------//
            SchoolJson postGradSchoolJson = profileDetails.getPostGradSchoolUserJson().getSchoolJson();
            School postGradSchool = getSchoolFromSchoolJson(postGradSchoolJson);

            SchoolUser postGradSchoolUser = saveSchoolUserFromSchoolUserJson(profileDetails.getPostGradSchoolUserJson(), user, postGradSchool);
            postGradSchoolUser.setSchoolUserType(SchoolUserType.POST_GRAD);
            schoolUserRepository.save(postGradSchoolUser);

            return profileDetails;
        } catch (Exception e) {
            throw new HourglassRestException(e.getMessage(), HourglassRestErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Company getCompanyFromCompanyJson(CompanyJson companyJson) {
        // If company id was not already set, search for company existence
        String companyName = companyJson.getName();
        Company existingCompany = companyRepository.getCompanyByName(companyName);
        if (existingCompany != null) {
            return existingCompany;
        } else {
            // If company is not existing in database, save it as new company
            return companyRepository.save(companyJson.createCompany());
        }
    }

    private Job saveJobFromJobJson(JobJson jobJson, User user, Company company) {
        // Check if current job already exists
        if (jobJson.getId() != null) {
            // Update existing current job
            Job existingCurrentJob = jobRepository.findById(jobJson.getId()).orElse(null);
            existingCurrentJob.setPosition(jobJson.getPosition());
            existingCurrentJob.setIndustry(jobJson.getIndustry());
            existingCurrentJob.setCompany(company);
            return jobRepository.save(existingCurrentJob);
        } else {
            // Create new job of correct job type
            Job currentJob = jobJson.createJob();
            currentJob.setCompany(company);
            currentJob.setUser(user);
            return jobRepository.save(currentJob);
        }
    }

    private School getSchoolFromSchoolJson(SchoolJson schoolJson) {
        String schoolName = schoolJson.getName();
        School existingSchool = schoolRepository.getSchoolByName(schoolName);
        if (existingSchool != null) {
            return existingSchool;
        } else {
            // If school is not existing in database, save it as new school
            return schoolRepository.save(schoolJson.createSchool());
        }
    }

    private SchoolUser saveSchoolUserFromSchoolUserJson(SchoolUserJson schoolUserJson, User user, School school) {
        // See if college school user already exists
        if (schoolUserJson.getId() != null) {
            // Update existing college school user
            SchoolUser existingSchoolUser = schoolUserRepository.findById(schoolUserJson.getId()).orElse(null);
            existingSchoolUser.setSchool(school);
            existingSchoolUser.setUser(user);
            existingSchoolUser.setEndDate(schoolUserJson.getEndDate());
            existingSchoolUser.setFieldOfStudy(schoolUserJson.getFieldOfStudy());
            existingSchoolUser.setDegree(schoolUserJson.getDegree());
            return schoolUserRepository.save(existingSchoolUser);
        } else {
            // Create new school user of correct type
            SchoolUser schoolUser = schoolUserJson.createSchoolUser();
            schoolUser.setUser(user);
            schoolUser.setSchool(school);
            return schoolUserRepository.save(schoolUser);
        }
    }

    /**
     *
     * @param userDisplayName
     * @return List of similar users that match user profile on at least one attribute
     *
     * [
     *   {
     *     similarUser: "",
     *     [
     *       {}
     *     ]
     *   }
     * ]
     *
     */
    public List<SimilarUser> getSimilarUsers(String userDisplayName) {
        ProfileDetails profileDetails = getProfileDetailsByUserDisplayName(userDisplayName);
        String collegeName = profileDetails.getCollegeSchoolUserJson().getSchoolJson().getName();
        String postGradSchoolName = profileDetails.getPostGradSchoolUserJson().getSchoolJson().getName();
        String currentJobPosition = profileDetails.getCurrentJobJson().getCompanyJson().getName();
        String currentJobCompany = profileDetails.getCurrentJobJson().getPosition();
        Industry currentJobIndustry = profileDetails.getCurrentJobJson().getIndustry();
        String firstPostCollegeJobPosition = profileDetails.getFirstPostCollegeJobJson().getPosition();
        String firstPostCollegeJobCompany = profileDetails.getFirstPostCollegeJobJson().getCompanyJson().getName();
        Industry firstPostCollegeJobIndustry = profileDetails.getFirstPostCollegeJobJson().getIndustry();
        String dreamJobPosition = profileDetails.getDreamJobJson().getPosition();
        String dreamJobCompany = profileDetails.getDreamJobJson().getCompanyJson().getName();
        Industry dreamJobIndustry = profileDetails.getDreamJobJson().getIndustry();

        HashMap<Long, SimilarUser> similarUserMap = new HashMap<>();

        // Find all schoolUsers with school name matching
        // For college
//        List<SchoolUser> schoolUsersMatchingCollege = schoolUserRepository.

        // For post-grad


        // Check for similar job




        return null;
    }
}
