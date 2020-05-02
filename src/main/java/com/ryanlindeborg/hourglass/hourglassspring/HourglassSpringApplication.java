package com.ryanlindeborg.hourglass.hourglassspring;

import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class HourglassSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HourglassSpringApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private SchoolRepository schoolRepository;
	@Autowired
	private SchoolUserRepository schoolUserRepository;

	@Bean
	// Start-up data for h2 database for development
	InitializingBean setUpDB() {
		// Create user
		User user = new User();
		user.setFirstName("Cameron");
		user.setLastName("Howe");
		Calendar birthDate = new GregorianCalendar(1965, 3, 2);
		user.setBirthDate(birthDate.getTime());
		user.setUserType(UserType.DEFAULT);

		// Create company
		Company company = new Company();
		company.setName("Mutiny");
		company.setIndustry(Industry.SOFTWARE_AND_COMPUTERS);

		// Create job
		Job job = new Job();
		job.setCompany(company);
		job.setPosition("Founder");
		job.setUser(user);
		Calendar startDate = new GregorianCalendar(1990, 6, 12);
		Calendar endDate = new GregorianCalendar(1992, 2, 18);
		job.setStartDate(startDate.getTime());
		job.setEndDate(endDate.getTime());

		// Create school
		School school = new School();
		school.setName("MIT");

		// Create school user
		SchoolUser schoolUser = new SchoolUser();
		schoolUser.setUser(user);
		schoolUser.setSchool(school);
		schoolUser.setDegree(Degree.BA);
		schoolUser.setFieldOfStudy("Computer Science");
		Calendar endDateSchool = new GregorianCalendar(1986, 5, 26);
		schoolUser.setEndDate(endDateSchool.getTime());
		schoolUser.setCompleted(Boolean.TRUE);

		return () -> {
			userRepository.save(user);
			companyRepository.save(company);
			jobRepository.save(job);
			schoolRepository.save(school);
			schoolUserRepository.save(schoolUser);
		};
	}

}
