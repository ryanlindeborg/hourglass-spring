package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.Company;
import com.ryanlindeborg.hourglass.hourglassspring.model.Industry;
import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobJson {
    private Long id;
    private UserJson userJson;
    private String position;
    private CompanyJson companyJson;
    private Industry industry;
    private Date startDate;
    private Date endDate;
    private JobType jobType;

    public Job createJob() {
        // Set blank json if they are null
        if (userJson == null) {
            userJson = UserJson.createEmptyUserJson();
        }
        if (companyJson == null) {
            companyJson = CompanyJson.createEmptyCompanyJson();
        }

        Job job = Job.builder()
                .id(id)
                .user(userJson.createUser())
                .position(position)
                .company(companyJson.createCompany())
                .industry(industry)
                .startDate(startDate)
                .endDate(endDate)
                .jobType(jobType)
                .build();

        return job;
    }

    public static JobJson createEmptyJobJson() {
        return new JobJson().builder()
                .userJson(UserJson.createEmptyUserJson())
                .companyJson(CompanyJson.createEmptyCompanyJson())
                .build();
    }
}
