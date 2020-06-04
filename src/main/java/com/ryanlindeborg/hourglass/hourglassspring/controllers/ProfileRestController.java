package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileDetails;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfileJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.ProfilePreview;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/profile", produces="application/json")
public class ProfileRestController {
    private ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Returns JSON of profile preview for all users
    // TODO: Figure out best way to paginate this
    @GetMapping("/previews")
    public List<ProfilePreview> getProfilePreviews() {
        return profileService.getProfilePreviews();
    }

    // Returns JSON of complete profile that front-end can parse and render into profile
    @GetMapping("/user/json/{id}")
    public ProfileJson getProfileJsonByUserId(@PathVariable("id") Long userId) {
        return profileService.getProfileJsonByUserId(userId);
    }

    @GetMapping("/user/{id}")
    public ProfileDetails getProfileDetailsByUserId(@PathVariable("id") Long userId) {
        return profileService.getProfileDetailsByUserId(userId);
    }

    // Parse profile information from biography form and create objects
    // TODO: could include 200 (or 204, or 201 - created) - which can include response body
    // TODO: Instead just return object or exception that has error code baked into it, but have to be able to pass in
    @PostMapping("/user")
    public ProfileDetails saveProfile(@Valid @RequestBody ProfileDetails profileDetails) {
        //TODO: Do more complex validation logic on object before call profileService method - can throw exceptions on it
        // Concatenate list of errors and errors can be displayed
        // If hit validation error, don't send initial data back, send list of errors

        return profileService.saveProfileFromJson(profileDetails);
    }
}
