package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
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
    public ProfileJson getProfileJsonByUserId(@PathVariable("id") Long userId) {
        return profileService.getProfileJsonByUserId(userId);
    }

    // Parse profile information from biography form and create objects
    @PostMapping("/user")
    public String saveProfile(@RequestBody String profileDetailsJson) {
        return profileService.saveProfileFromJson(profileDetailsJson);
    }
}
