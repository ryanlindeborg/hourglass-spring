package com.ryanlindeborg.hourglass.hourglassspring;

import com.ryanlindeborg.hourglass.hourglassspring.model.*;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

@SpringBootApplication
public class HourglassSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HourglassSpringApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;
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

	@Value(value = "${vue.client.url}")
	String clientUrl;

	@Bean
	// Start-up data for h2 database for development
	InitializingBean setUpDB() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//TODO: Implement builder pattern for these objects maybe
		// Create user
		User user = new User();
		user.setRoles(Arrays.asList(new Role("USER")));
		user.setFirstName("Cameron");
		user.setLastName("Howe");
		Calendar birthDate = new GregorianCalendar(1965, 3, 2);
		user.setBirthDate(birthDate.getTime());
		user.setEmail("chowe@mailinator.com");
		user.setUsername("cameronh");
		user.setDisplayName("cameron_howe");
		user.setImageSquareName("elon-musk-square");
		user.setImageRectangleName("elon-musk-rectangle");
		user.setPasswordHash(passwordEncoder.encode("helloworld123"));
		user.setUserType(UserType.DEFAULT);

		User user2 = new User();
		user2.setRoles(Arrays.asList(new Role("ADMIN")));
		user2.setFirstName("Gordon");
		user2.setLastName("Clark");
		Calendar birthDate2 = new GregorianCalendar(1951, 9, 17);
		user2.setBirthDate(birthDate2.getTime());
		user2.setEmail("gclark@mailinator.com");
		user2.setUsername("gordonc");
		user2.setDisplayName("gordon_clark");
		user2.setImageSquareName("larry-page-square");
		user2.setImageRectangleName("larry-page-rectangle");
		user2.setPasswordHash(passwordEncoder.encode("helloworld456"));
		user2.setUserType(UserType.DEFAULT);

		// Create companies
		Company company = new Company();
		company.setName("Mutiny");
		company.setIndustry(Industry.SOFTWARE_AND_COMPUTERS);
		Company company2 = new Company();
		company2.setName("SpaceX");
		company2.setIndustry(Industry.AEROSPACE);
		Company company3 = new Company();
		company3.setName("JP Morgan");
		company3.setIndustry(Industry.FINANCE);

		// Create current job
		Job job = new Job();
		job.setCompany(company);
		job.setPosition("Founder");
		job.setUser(user);
		Calendar startDate = new GregorianCalendar(1990, 6, 12);
		job.setStartDate(startDate.getTime());
		job.setJobType(JobType.CURRENT_JOB);

		// Create first post college job
		Job job2 = new Job();
		job2.setCompany(company2);
		job2.setPosition("Flight Engineer");
		job2.setUser(user);
		Calendar startDate2 = new GregorianCalendar(1992, 2, 18);
		Calendar endDate2 = new GregorianCalendar(1997, 7, 15);
		job2.setStartDate(startDate2.getTime());
		job2.setEndDate(endDate2.getTime());
		job2.setJobType(JobType.FIRST_POST_COLLEGE_JOB);

		Job job4 = new Job();
		job4.setCompany(company2);
		job4.setPosition("Senior Rocket Engineer");
		job4.setUser(user2);
		Calendar startDate4 = new GregorianCalendar(1987, 4, 22);
		job4.setStartDate(startDate4.getTime());
		job4.setJobType(JobType.CURRENT_JOB);

		// Create dream job
		Job job3 = new Job();
		job3.setCompany(company3);
		job3.setPosition("Management Director");
		job3.setUser(user);
		job3.setJobType(JobType.DREAM_JOB);


		// Create school
		School school = new School();
		school.setName("MIT");
		School school2 = new School();
		school2.setName("Harvard");

		// Create college school user
		SchoolUser collegeSchoolUser = new SchoolUser();
		collegeSchoolUser.setUser(user);
		collegeSchoolUser.setSchool(school);
		collegeSchoolUser.setDegree(Degree.BA);
		collegeSchoolUser.setFieldOfStudy("Computer Science");
		Calendar endDateSchool = new GregorianCalendar(1986, 5, 26);
		collegeSchoolUser.setEndDate(endDateSchool.getTime());
		collegeSchoolUser.setIsCompleted(Boolean.TRUE);
		collegeSchoolUser.setSchoolUserType(SchoolUserType.COLLEGE);

		// Create post-grad school user
		SchoolUser postGradSchoolUser = new SchoolUser();
		postGradSchoolUser.setUser(user);
		postGradSchoolUser.setSchool(school2);
		postGradSchoolUser.setDegree(Degree.MBA);
		postGradSchoolUser.setFieldOfStudy("Entrepreneurship");
		Calendar endDatePostGradSchool = new GregorianCalendar(1988, 6, 1);
		postGradSchoolUser.setEndDate(endDatePostGradSchool.getTime());
		postGradSchoolUser.setIsCompleted(Boolean.TRUE);
		postGradSchoolUser.setSchoolUserType(SchoolUserType.POST_GRAD);

		Role userRole = new Role();
		userRole.setName("USER");

		Role adminRole = new Role();
		adminRole.setName("ADMIN");


		return () -> {
			roleRepository.save(adminRole);
			roleRepository.save(userRole);
			userRepository.save(user);
			userRepository.save(user2);
			companyRepository.save(company);
			companyRepository.save(company2);
			companyRepository.save(company3);
			jobRepository.save(job);
			jobRepository.save(job2);
			jobRepository.save(job3);
			jobRepository.save(job4);
			schoolRepository.save(school);
			schoolRepository.save(school2);
			schoolUserRepository.save(collegeSchoolUser);
			schoolUserRepository.save(postGradSchoolUser);
		};
	}

	// CORS filter defined to allow API requests specifically from client
	@Bean
	public FilterRegistrationBean simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// Dynamically set the allowed origins of the application based on environment
		config.setAllowedOrigins(Collections.singletonList(clientUrl));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
