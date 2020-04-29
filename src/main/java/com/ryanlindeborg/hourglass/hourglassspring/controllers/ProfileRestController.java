package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequestMapping(path="/api/v1/profile", produces="application/json")
@CrossOrigin(origins="*")
public class ProfileRestController {
    private ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Returns JSON of complete profile that front-end can parse and render into profile
    @GetMapping("/user/{id}")
    public ProfileJson getProfileJsonByUserId(@PathVariable("id") Long userId) {
        return profileService.getProfileJsonByUserId(userId);
    }

    // Temp, just for testing purposes
    // Example profile json for test
    @GetMapping("/user/test")
    public ProfileJson getProfileJson() {
        ProfileJson profileJson = new ProfileJson();

        // Grab user
        User user = new User();
        user.setFirstName("Cameron");
        user.setLastName("Howe");
        Calendar birthDate = new GregorianCalendar(1965, 3, 2);
        user.setBirthDate(birthDate.getTime());
        user.setUserType(UserType.DEFAULT);
        profileJson.setUser(user);

        // Grab list of jobs associated with user
        Company company = new Company();
        company.setName("Mutiny");
        company.setIndustry(Industry.SOFTWARE_AND_COMPUTERS);

        Job job = new Job();
        job.setCompany(company);
        job.setPosition("Founder");
        job.setUser(user);
        Calendar startDate = new GregorianCalendar(1990, 6, 12);
        Calendar endDate = new GregorianCalendar(1992, 2, 18);
        job.setStartDate(startDate.getTime());
        job.setEndDate(endDate.getTime());
        profileJson.setJobs(Arrays.asList(job));

        // Grab list of school-user records associated with that user
        School school = new School();
        school.setName("MIT");

        SchoolUser schoolUser = new SchoolUser();
        schoolUser.setUser(user);
        schoolUser.setSchool(school);
        schoolUser.setDegree(Degree.BA);
        schoolUser.setFieldOfStudy("Computer Science");
        Calendar endDateSchool = new GregorianCalendar(1986, 5, 26);
        schoolUser.setEndDate(endDateSchool.getTime());
        schoolUser.setCompleted(Boolean.TRUE);
        profileJson.setSchoolUsers(Arrays.asList(schoolUser));

        return profileJson;
    }
}
