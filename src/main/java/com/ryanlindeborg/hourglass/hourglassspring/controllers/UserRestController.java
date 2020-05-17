package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/user", produces="application/json")
public class UserRestController {
    private UserRepository userRepository;

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
}
