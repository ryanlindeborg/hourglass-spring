package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.exception.HourglassRestException;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/profile", produces="application/json")
@CrossOrigin(origins="*")
public class ProfileRestController {
    private ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Returns JSON of complete profile that front-end can parse and render into profile
    @GetMapping("/user/{id}")
    public ResponseEntity<> getProfileJsonByUserId(@PathVariable("id") Long userId) {
        User user = profileService.getUserById(userId);

        // Return standard exception with error code and message across whole project
        if (user == null) {
            return new ResponseEntity<HourglassRestException>(new HourglassRestException("User profile does not exist", HourglassRestErrorCode.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return null;
    }
}
