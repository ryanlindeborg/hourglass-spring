package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private JobRepository jobRepository;
    private SchoolRepository schoolRepository;
    private SchoolUserRepository schoolUserRepository;

    public ProfileService(UserRepository userRepository, CompanyRepository companyRepository, JobRepository jobRepository, SchoolRepository schoolRepository, SchoolUserRepository schoolUserRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.schoolRepository = schoolRepository;
        this.schoolUserRepository = schoolUserRepository;
    }

    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }
}
