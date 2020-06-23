package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.LoginDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.RegistrationDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.security.AuthenticationToken;
import com.ryanlindeborg.hourglass.hourglassspring.security.HourglassUserDetailsService;
import com.ryanlindeborg.hourglass.hourglassspring.security.JwtAuthenticationFilter;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import com.ryanlindeborg.hourglass.hourglassspring.security.JwtService;
import com.ryanlindeborg.hourglass.hourglassspring.security.SecurityUtils;
import com.ryanlindeborg.hourglass.hourglassspring.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path="/api/v1/user", produces="application/json")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    public User postUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Get list of all users
    @GetMapping("/users")
    public List<User> getUsers() {
        Pageable firstTenUsers = PageRequest.of(0, 10);
        return userRepository.findAll(firstTenUsers).getContent();
    }
    // TODO: Front end technology will have lazy-loaded table you can use
    // TODO: Could have getUsersByRange(int start, int end) method
    // TODO: could also have getUsersBy[Object] - getUsersByIndustry

    @PostMapping("/session")
    public ResponseEntity<AuthenticationToken> login(@RequestBody LoginDetails credentials) {
        User user = null;

        String loginName = credentials.getLoginName();
        // Parse to see if user is logging in via username or email
        // TODO: Find Java library to replace this temporary check for email or username parsing
        if (loginName.contains("@") && loginName.contains(".")) {
            String email = loginName;
            user = userRepository.getUserByEmail(email);
        } else {
            String username = loginName;
            user = userRepository.getUserByUsername(username);
        }

        String password = credentials.getPassword();

        // Verify password hash matches
        if(user == null || !new BCryptPasswordEncoder().matches(password, user.getPasswordHash())) {
            throw new AccessDeniedException("Incorrect credentials. Please try again.");
        }


        Date currentDate = new Date();
        String jwt = Jwts.builder()
                .setSubject(user.getDisplayName())
                .setIssuedAt(currentDate)
                // Set expiration date for 2 hours
                // TODO: Make util class for time functions - getDateHoursAgo, getCurrentDate
                .setExpiration(new Date(currentDate.getTime() + 2 * 60 * 60 * 1000L))
                .signWith(Keys.hmacShaKeyFor(jwtService.getJWTSecret()))
                .compact();

        return new ResponseEntity<>(new AuthenticationToken(jwt), HttpStatus.CREATED);
    }

    @PostMapping("/registration")
    public User register(@RequestBody RegistrationDetails registrationDetails) {
        return userService.registerUser(registrationDetails);
    }
}
