package com.ryanlindeborg.hourglass.hourglassspring.model;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.CompanyJson;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Industry industry;

    public CompanyJson createCompanyJson() {
        CompanyJson companyJson = CompanyJson.builder()
                .id(id)
                .name(name)
                .industry(industry)
                .build();

        return companyJson;
    }
}
