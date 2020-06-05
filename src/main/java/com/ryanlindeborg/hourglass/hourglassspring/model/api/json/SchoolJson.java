package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class SchoolJson {
    private Long id;
    private String name;
}
