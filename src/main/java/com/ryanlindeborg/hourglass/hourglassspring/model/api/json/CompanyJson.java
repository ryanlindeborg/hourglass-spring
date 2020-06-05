package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.Industry;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@Builder
public class CompanyJson {
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Industry industry;
}
