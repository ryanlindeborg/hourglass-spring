package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.JobJson;
import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.UserJson;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing information needed for preview profile display
 */
@Data
@NoArgsConstructor
public class ProfilePreview {
    private UserJson userJson;
    private JobJson currentJobJson;
}
