package com.ryanlindeborg.hourglass.hourglassspring.services;

import com.ryanlindeborg.hourglass.hourglassspring.HourglassUtil;
import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.LoginDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.RegistrationDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.security.AuthenticationToken;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import com.ryanlindeborg.hourglass.hourglassspring.security.JwtAuthenticationFilter;
import com.ryanlindeborg.hourglass.hourglassspring.security.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public User registerUser(RegistrationDetails registrationDetails) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registrationDetails.getPassword());

        String userName = registrationDetails.getUsername();
        String displayName = registrationDetails.getDisplayName();

        // Validate that username ad displayName are not already taken
        List<String> errors = new ArrayList<>();
        if (userRepository.getUserByUsername(userName) != null) {
            errors.add("That username is not available. Please select a different one.");
        }
        if (userRepository.getUserByDisplayName(displayName) != null) {
            errors.add("That profile handle is not available. Please select a different one.");
        }

        if (!HourglassUtil.isEmptyOrNull(errors)) {
            throw new HourglassRestException()
                    .builder()
                    .message("User registration failed")
                    .errors(errors)
                    .build();
        }

        User user = User.builder()
                .firstName(registrationDetails.getFirstName())
                .lastName(registrationDetails.getLastName())
                .email(registrationDetails.getEmail())
                .username(userName)
                .displayName(displayName)
                .passwordHash(hashedPassword)
                .build();

        return userRepository.save(user);
    }

    public ResponseEntity<AuthenticationToken> login(LoginDetails credentials)  {
        User user = null;

        String loginName = credentials.getLoginName();
        // Parse to see if user is logging in via username or email
        if (HourglassUtil.isValidEmail(loginName)) {
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


        @NotBlank(message = "Profile display name is required") String displayName = user.getDisplayName();
        return issueAndRespondWithJWT(displayName);
    }

    public ResponseEntity<AuthenticationToken> issueAndRespondWithJWT(@NotBlank(message = "Profile display name is required") String displayName) {
        String jwt = issueJWT(displayName);

        return new ResponseEntity<>(new AuthenticationToken(jwt), HttpStatus.CREATED);
    }

    public String issueJWT(@NotBlank(message = "Profile display name is required") String displayName) {
        Date currentDate = new Date();
        return Jwts.builder()
                .setSubject(displayName)
                .setIssuedAt(currentDate)
                // Set expiration date for 2 hours
                .setExpiration(HourglassUtil.getDateHoursAgo(currentDate, -2))
                .signWith(Keys.hmacShaKeyFor(jwtService.getJWTSecret()))
                .compact();
    }

    public ResponseEntity logoutUser(String displayName) {
        User user = userRepository.getUserByDisplayName(displayName);
        Long currentTimestamp = new Date().getTime();
        // This will set all tokens issued before this timestamp as invalid
        user.setMinJwtIssuedTimestamp(currentTimestamp);
        userRepository.save(user);
        // Remove JWT from the cookies by setting it to blank and expired
        Map<String, List<String>> headers = Collections.singletonMap(HttpHeaders.SET_COOKIE,
                Collections.singletonList(
                        String.format("%s=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; HttpOnly",
                                JwtAuthenticationFilter.JWT_COOKIE)));
        return new ResponseEntity(CollectionUtils.toMultiValueMap(headers),HttpStatus.OK);
    }
}
