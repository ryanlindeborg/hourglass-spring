package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/profile", produces="application/json")
public class ProfileRestController {
    private ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Returns JSON of complete profile that front-end can parse and render into profile
    @GetMapping("/user/{id}")
    public ProfileJson getProfileJsonByUserId(@PathVariable("id") Long userId) {
        return profileService.getProfileJsonByUserId(userId);
    }

    // Parse profile information from biography form and create objects
    // TODO: could include 200 (or 204, or 201 - created) - which can include response body
    // TODO: Instead just return object or exception that has error code baked into it, but have to be able to pass in
    @PostMapping("/user")
    public ProfileDetails saveProfile(@RequestBody ProfileDetails profileDetails) {
        return profileService.saveProfileFromJson(profileDetails);
    }
}
