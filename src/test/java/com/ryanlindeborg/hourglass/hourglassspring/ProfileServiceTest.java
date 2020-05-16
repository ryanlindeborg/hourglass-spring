package com.ryanlindeborg.hourglass.hourglassspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.JobRepository;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.SchoolUserRepository;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HourglassSpringApplication.class)
@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    JobRepository jobRepository;
    @Mock
    SchoolUserRepository schoolUserRepository;

    @InjectMocks
    ProfileService profileService;

    @Test
    public void testGetUserById() {
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        assertEquals(user, profileService.getUserById(1L));
    }

    @Test
    public void testGetProfileJsonByUserId() {
        String profileJsonString = "{\"user\":{\"id\":null,\"firstName\":\"Cameron\",\"middleName\":null,\"lastName\":\"Howe\",\"birthDate\":\"1965-04-02T08:00:00.000+0000\",\"userType\":\"DEFAULT\"},\"jobs\":[{\"id\":null,\"user\":{\"id\":null,\"firstName\":\"Cameron\",\"middleName\":null,\"lastName\":\"Howe\",\"birthDate\":\"1965-04-02T08:00:00.000+0000\",\"userType\":\"DEFAULT\"},\"position\":\"Founder\",\"company\":{\"id\":null,\"name\":\"Mutiny\",\"industry\":\"SOFTWARE_AND_COMPUTERS\"},\"industry\":null,\"startDate\":\"1990-07-12T07:00:00.000+0000\",\"endDate\":\"1992-03-18T08:00:00.000+0000\"}],\"schoolUsers\":[{\"id\":null,\"user\":{\"id\":null,\"firstName\":\"Cameron\",\"middleName\":null,\"lastName\":\"Howe\",\"birthDate\":\"1965-04-02T08:00:00.000+0000\",\"userType\":\"DEFAULT\"},\"school\":{\"id\":null,\"name\":\"MIT\"},\"startDate\":null,\"endDate\":\"1986-06-26T07:00:00.000+0000\",\"degree\":\"BA\",\"fieldOfStudy\":\"Computer Science\",\"isCompleted\":true}]}";
//        String profileJsonString = "{\"user\":{\"id\":null,\"firstName\":\"Cameron\",\"middleName\":null,\"lastName\":\"Howe\",\"birthDate\":\"Fri Apr 02 00:00:00 PST 1965\",\"userType\":\"DEFAULT\"},\"jobs\":[{\"id\":null,\"user\":{\"id\":null,\"firstName\":\"Cameron\",\"middleName\":null,\"lastName\":\"Howe\",\"birthDate\":\"1965-04-02T08:00:00.000+0000\",\"userType\":\"DEFAULT\"},\"position\":\"Founder\",\"company\":{\"id\":null,\"name\":\"Mutiny\",\"industry\":\"SOFTWARE_AND_COMPUTERS\"},\"industry\":null,\"startDate\":\"1990-07-12T07:00:00.000+0000\",\"endDate\":\"1992-03-18T08:00:00.000+0000\"}],\"schoolUsers\":[{\"id\":null,\"user\":{\"id\":null,\"firstName\":\"Cameron\",\"middleName\":null,\"lastName\":\"Howe\",\"birthDate\":\"1965-04-02T08:00:00.000+0000\",\"userType\":\"DEFAULT\"},\"school\":{\"id\":null,\"name\":\"MIT\"},\"startDate\":null,\"endDate\":\"1986-06-26T07:00:00.000+0000\",\"degree\":\"BA\",\"fieldOfStudy\":\"Computer Science\",\"isCompleted\":true}]}";

        //TODO: Implement builder pattern for these objects
        // Provide data for user
        User user = new User();
        user.setFirstName("Cameron");
        user.setLastName("Howe");
        Calendar birthDate = new GregorianCalendar(1965, 3, 2);
        user.setBirthDate(birthDate.getTime());
        user.setUserType(UserType.DEFAULT);

        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);

        // Provide data for Job
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

        when(jobRepository.getJobsByUserId(Mockito.anyLong())).thenReturn(Arrays.asList(job));

        // Provide data for SchoolUser
        School school = new School();
        school.setName("MIT");

        SchoolUser schoolUser = new SchoolUser();
        schoolUser.setUser(user);
        schoolUser.setSchool(school);
        schoolUser.setDegree(Degree.BA);
        schoolUser.setFieldOfStudy("Computer Science");
        Calendar endDateSchool = new GregorianCalendar(1986, 5, 26);
        schoolUser.setEndDate(endDateSchool.getTime());
        schoolUser.setIsCompleted(Boolean.TRUE);

        when(schoolUserRepository.getSchoolUsersByUserId(Mockito.anyLong())).thenReturn(Arrays.asList(schoolUser));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            assertEquals(objectMapper.readValue(profileJsonString, ProfileJson.class), profileService.getProfileJsonByUserId(1L));
        } catch (Exception e) {
            fail("Couldn't parse test profile json string properly");
        }
    }

    @Test
    public void testGetProfileJsonForNullUser() {
//        when(profileService.getUserById(Mockito.anyLong())).thenReturn(null);
        // TODO: What would be best way to pass back null user from profileService mock?
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(HourglassRestException.class, () -> {
            profileService.getProfileJsonByUserId(1L);
        });
    }
}
