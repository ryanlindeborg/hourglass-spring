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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Calendar;
import java.util.Collections;
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

	@Value(value = "${vue.client.url}")
	String clientUrl;

	@Bean
	// Start-up data for h2 database for development
	InitializingBean setUpDB() {
		//TODO: Implement builder pattern for these objects
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
		schoolUser.setIsCompleted(Boolean.TRUE);

		return () -> {
			userRepository.save(user);
			companyRepository.save(company);
			jobRepository.save(job);
			schoolRepository.save(school);
			schoolUserRepository.save(schoolUser);
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
