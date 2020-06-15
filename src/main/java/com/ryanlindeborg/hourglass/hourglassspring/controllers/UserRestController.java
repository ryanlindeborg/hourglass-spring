package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.security.HourglassUserDetailsService;
import com.ryanlindeborg.hourglass.hourglassspring.security.JwtAuthenticationFilter;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
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

import java.util.*;

@RestController
@RequestMapping(path="/api/v1/user", produces="application/json")
public class UserRestController {
    private UserRepository userRepository;

    // TODO: Figure out if can delete this userDetailsService from this class
//    @Autowired
//    private HourglassUserDetailsService userDetailsService;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    // TODO: Convert this String map to its own object
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userRepository.getUserByDisplayName(username);
        // Verify password hash matches
        if(user == null || !new BCryptPasswordEncoder().matches(password, user.getPasswordHash())) {
            throw new AccessDeniedException("Incorrect credentials. Please try again.");
        }


        Date currentDate = new Date();
        String jwt = Jwts.builder()
                .setSubject(user.getDisplayName())
                .setIssuedAt(currentDate)
                // Set expiration date for 2 hours
                .setExpiration(new Date(currentDate.getTime() + 2 * 60 * 60 * 1000L))
                .signWith(Keys.hmacShaKeyFor(JwtAuthenticationFilter.SECRET.getBytes()))
                .compact();

        return new ResponseEntity<>(Collections.singletonMap("token", jwt), HttpStatus.CREATED);
    }
}
