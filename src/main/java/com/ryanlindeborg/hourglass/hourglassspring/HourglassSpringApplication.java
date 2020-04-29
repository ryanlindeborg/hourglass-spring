package com.ryanlindeborg.hourglass.hourglassspring;

import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HourglassSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HourglassSpringApplication.class, args);
	}

	@Bean
	InitializingBean setUpDB() {
		// Autowire in repositories and call save methods to save to h2 database
//		@Autowired
//		private UserRepository userRepository;

		return () -> {
			// Might have object creation in here (e.g., create a job)

//			yourRepository1.save(Object1);
//			yourRepository2.save(Object2);
		};
	}

}
