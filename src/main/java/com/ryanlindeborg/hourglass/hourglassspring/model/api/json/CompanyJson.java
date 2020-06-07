package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.Company;
import com.ryanlindeborg.hourglass.hourglassspring.model.Industry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyJson {
    private Long id;
    private String name;
    private Industry industry;

    public Company createCompany() {
        Company company = Company.builder()
                .id(id)
                .name(name)
                .industry(industry)
                .build();

        return company;
    }
}
