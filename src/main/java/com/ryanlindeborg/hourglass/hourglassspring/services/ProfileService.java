package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.CompanyJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.JobJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.SchoolUserJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.UserJson;
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

    public List<ProfilePreview> getProfilePreviews() {
        //TODO: Update this to take a range maybe for pagination
        List<User> users = userRepository.findAll();
        List<ProfilePreview> profilePreviews = new ArrayList<>();

        for (User user : users) {
            ProfilePreview profilePreview = new ProfilePreview();
            profilePreview.setUser(user);

            Job currentJob = jobRepository.getCurrentJobForUserByDisplayName(user.getDisplayName());
            if (currentJob != null) {
                profilePreview.setCurrentJob(currentJob);
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
        List<Job> jobs = jobRepository.getJobsByUserDisplayName(displayName);
        profileJson.setJobs(jobs);

        // Grab list of school-user records associated with that user
        List<SchoolUser> schoolUsers = schoolUserRepository.getSchoolUsersByUserDisplayName(displayName);
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

        Job currentJob = jobRepository.getCurrentJobForUserByDisplayName(displayName);
        if (currentJob != null) {
            JobJson currentJobJson = currentJob.createJobJson();
            profileDetails.setCurrentJobJson(currentJobJson);
        }

        Job firstPostCollegeJob = jobRepository.getFirstPostCollegeJobForUserByDisplayName(displayName);
        if (firstPostCollegeJob != null) {
            JobJson firstPostCollegeJobJson = firstPostCollegeJob.createJobJson();
            profileDetails.setFirstPostCollegeJobJson(firstPostCollegeJobJson);
        }

        Job dreamJob = jobRepository.getDreamJobForUserByDisplayName(displayName);
        if (dreamJob != null) {
            JobJson dreamJobJson = dreamJob.createJobJson();
            profileDetails.setDreamJobJson(dreamJobJson);
        }

        SchoolUser collegeSchoolUser = schoolUserRepository.getCollegeSchoolUserByUserDisplayName(displayName);
        if (collegeSchoolUser != null) {
            SchoolUserJson collegeSchoolUserJson = collegeSchoolUser.createSchoolUserJson();
            profileDetails.setCollegeSchoolUserJson(collegeSchoolUserJson);
        }

        SchoolUser postGradSchoolUser = schoolUserRepository.getPostGradSchoolUserByUserDisplayName(displayName);
        if (postGradSchoolUser != null) {
            SchoolUserJson postGradSchoolUserJson = postGradSchoolUser.createSchoolUserJson();
            profileDetails.setPostGradSchoolUserJson(postGradSchoolUserJson);
        }

        return profileDetails;
    }

    /**
     *
     * @param profileDetails
     * @return profileDetails that was passed into method back to front end so can edit if hit validation error
     */
    //TODO: 6/5/2020: Edit objects from json objects
    @Transactional
    public ProfileDetails saveProfileFromJson(ProfileDetails profileDetails) {
        try {
            //--------- Process user ---------//
            UserJson userJson = profileDetails.getUserJson();
            User user = null;
            // Check if user already exists
            if (userJson.getId() != null) {
                // Update existing user
                User existingUser = userRepository.findById(userJson.getId()).orElse(null);
                existingUser.setFirstName(userJson.getFirstName());
                existingUser.setLastName(userJson.getLastName());
                existingUser.setBirthDate(userJson.getBirthDate());
                user = userRepository.save(existingUser);
            } else {
                user = userJson.createUser();
                // Save user as default user
                user.setUserType(UserType.DEFAULT);

                user = userRepository.save(user);
            }

            //--------- Process currentJob ---------//
            JobJson currentJobJson = profileDetails.getCurrentJobJson();
            CompanyJson currentJobCompanyJson = profileDetails.getCurrentJobJson().getCompanyJson();
            Company currentJobCompany = null;

            // See if current job company is existing
            if (currentJobCompanyJson.getId() != null) {
                currentJobCompany = companyRepository.findById(currentJobCompanyJson.getId()).orElse(null);
            } else {
                // If company id was not already set, search for company existence
                String currentJobCompanyName = currentJobCompany.getName();
                Company existingCurrentJobCompany = companyRepository.getCompanyByName(currentJobCompanyName);
                if (existingCurrentJobCompany != null) {
                    currentJobCompany = existingCurrentJobCompany;
                } else {
                    // If company is not existing in database, save it as new company
                    currentJobCompany = companyRepository.save(currentJobCompanyJson.createCompany());
                }
            }

            // Check if current job already exists
            if (currentJobJson.getId() != null) {
                // Update existing current job
                Job existingCurrentJob = jobRepository.findById(currentJobJson.getId()).orElse(null);
                existingCurrentJob.setPosition(currentJobJson.getPosition());
                existingCurrentJob.setIndustry(currentJobJson.getIndustry());
                existingCurrentJob.setCompany(currentJobCompany);
                jobRepository.save(existingCurrentJob);
            } else {
                // Create new job of correct job type
                Job currentJob = currentJobJson.createJob();
                currentJob.setCompany(currentJobCompany);
                currentJob.setUser(user);
                currentJob.setJobType(JobType.CURRENT_JOB);
                jobRepository.save(currentJob);
            }

            //--------- Process firstPostCollegeJob ---------//
            JobJson firstPostCollegeJobJson = profileDetails.getFirstPostCollegeJobJson();
            CompanyJson firstPostCollegeJobCompanyJson = profileDetails.getFirstPostCollegeJobJson().getCompanyJson();
            Company firstPostCollegeJobCompany = null;

            // See if first post-college job company is existing
            if (firstPostCollegeJobCompanyJson.getId() != null) {
                firstPostCollegeJobCompany = companyRepository.findById(firstPostCollegeJobCompany.getId()).orElse(null);
            } else {
                // If company id was not already set, search for company existence
                String firstPostCollegeJobCompanyName = firstPostCollegeJobCompany.getName();
                Company existingFirstPostCollegeJobCompany = companyRepository.getCompanyByName(firstPostCollegeJobCompanyName);
                if (existingFirstPostCollegeJobCompany != null) {
                    firstPostCollegeJobCompany = existingFirstPostCollegeJobCompany;
                } else {
                    // If company is not existing in database, save it as new company
                    firstPostCollegeJobCompany = companyRepository.save(firstPostCollegeJobCompany);
                }
            }

            // Check if first post-college job already exists
            if (firstPostCollegeJobJson.getId() != null) {
                // Update existing first post-college job
                Job existingFirstPostCollegeJob = jobRepository.findById(firstPostCollegeJobJson.getId()).orElse(null);
                existingFirstPostCollegeJob.setPosition(firstPostCollegeJobJson.getPosition());
                existingFirstPostCollegeJob.setIndustry(firstPostCollegeJobJson.getIndustry());
                existingFirstPostCollegeJob.setCompany(firstPostCollegeJobCompany);
                jobRepository.save(existingFirstPostCollegeJob);
            } else {
                // Create new job of correct job type
                Job firstPostCollegeJob = firstPostCollegeJobJson.createJob();
                firstPostCollegeJob.setCompany(firstPostCollegeJobCompany);
                firstPostCollegeJob.setUser(user);
                firstPostCollegeJob.setJobType(JobType.FIRST_POST_COLLEGE_JOB);
                firstPostCollegeJob.setCompany(firstPostCollegeJobCompany);
                jobRepository.save(firstPostCollegeJob);
            }

            //--------- Process dreamJob ---------//
            Job dreamJob = profileDetails.getDreamJob();
            Job existingDreamJob = null;
            Company dreamJobCompany = profileDetails.getDreamJob().getCompany();

            // See if dream job company is existing
            if (dreamJobCompany.getId() != null) {
                dreamJobCompany = companyRepository.findById(dreamJobCompany.getId()).orElse(null);
            } else {
                // If company id was not already set, search for company existence
                String dreamJobCompanyName = dreamJobCompany.getName();
                Company existingDreamJobCompany = companyRepository.getCompanyByName(dreamJobCompanyName);
                if (existingDreamJobCompany != null) {
                    dreamJobCompany = existingDreamJobCompany;
                } else {
                    // If company is not existing in database, save it as new company
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
            }
            jobRepository.save(dreamJob);

            //--------- Process collegeSchoolUser ---------//
            SchoolUser collegeSchoolUser = profileDetails.getCollegeSchoolUser();
            SchoolUser existingCollegeSchoolUser = null;
            School college = profileDetails.getCollegeSchoolUser().getSchool();

            // See if school is existing
            if (college.getId() != null) {
                college = schoolRepository.findById(college.getId()).orElse(null);
            } else {
                // If school not already set, search for school existence
                String collegeName = college.getName();
                School existingSchool = schoolRepository.getSchoolByName(collegeName);
                if (existingSchool != null) {
                    college = existingSchool;
                } else {
                    // If school is not existing in database, save it as new school
                    college = schoolRepository.save(college);
                }
            }


            // See if college school already exists
            if (collegeSchoolUser.getId() != null) {
                // Update existing college school user
                existingCollegeSchoolUser = schoolUserRepository.findById(collegeSchoolUser.getId()).orElse(null);
                existingCollegeSchoolUser.setSchool(college);
                existingCollegeSchoolUser.setUser(user);
                existingCollegeSchoolUser.setEndDate(collegeSchoolUser.getEndDate());
                existingCollegeSchoolUser.setFieldOfStudy(collegeSchoolUser.getFieldOfStudy());
                collegeSchoolUser = existingCollegeSchoolUser;
            } else {
                // Create new school user of correct type
                collegeSchoolUser.setUser(user);
                collegeSchoolUser.setSchool(college);
                collegeSchoolUser.setSchoolUserType(SchoolUserType.COLLEGE);
            }
            schoolUserRepository.save(collegeSchoolUser);

            //--------- Process postGradSchoolUser ---------//
            // school, end date, degree, field of study
            SchoolUser postGradSchoolUser = profileDetails.getPostGradSchoolUser();
            SchoolUser existingPostGradSchoolUser = null;
            School postGradSchool = profileDetails.getPostGradSchoolUser().getSchool();

            // See if school is existing
            if (postGradSchool.getId() != null) {
                postGradSchool = schoolRepository.findById(postGradSchool.getId()).orElse(null);
            } else {
                // If school not already set, search for school existence
                String postGradSchoolName = postGradSchool.getName();
                School existingSchool = schoolRepository.getSchoolByName(postGradSchoolName);
                if (existingSchool != null) {
                    postGradSchool = existingSchool;
                } else {
                    // If school is not existing in database, save it as new school
                    postGradSchool = schoolRepository.save(postGradSchool);
                }
            }


            // See if post grad school already exists
            if (postGradSchoolUser.getId() != null) {
                // Update existing post grad school user
                existingPostGradSchoolUser = schoolUserRepository.findById(postGradSchoolUser.getId()).orElse(null);
                existingPostGradSchoolUser.setSchool(postGradSchool);
                existingPostGradSchoolUser.setUser(user);
                existingPostGradSchoolUser.setEndDate(postGradSchoolUser.getEndDate());
                existingPostGradSchoolUser.setFieldOfStudy(postGradSchoolUser.getFieldOfStudy());
                existingPostGradSchoolUser.setDegree(postGradSchoolUser.getDegree());
                postGradSchoolUser = existingPostGradSchoolUser;
            } else {
                // Create new school user of correct type
                postGradSchoolUser.setUser(user);
                postGradSchoolUser.setSchool(postGradSchool);
                postGradSchoolUser.setSchoolUserType(SchoolUserType.POST_GRAD);
            }
            schoolUserRepository.save(postGradSchoolUser);

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
