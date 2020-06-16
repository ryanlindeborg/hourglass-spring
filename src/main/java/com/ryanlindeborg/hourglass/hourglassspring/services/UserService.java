package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.RegistrationDetails;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(RegistrationDetails registrationDetails) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registrationDetails.getPassword());

        User user = User.builder()
                .firstName(registrationDetails.getFirstName())
                .lastName(registrationDetails.getLastName())
                .email(registrationDetails.getEmail())
                .username(registrationDetails.getUsername())
                .displayName(registrationDetails.getDisplayName())
                .passwordHash(hashedPassword)
                .build();

        return userRepository.save(user);
    }
}
