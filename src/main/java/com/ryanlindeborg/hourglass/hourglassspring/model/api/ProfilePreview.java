package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing information needed for preview profile display
 */
@Data
@NoArgsConstructor
public class ProfilePreview {
    private User user;
    private Job currentJob;
}
