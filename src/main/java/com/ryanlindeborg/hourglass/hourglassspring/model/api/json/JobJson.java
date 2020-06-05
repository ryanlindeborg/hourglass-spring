package com.ryanlindeborg.hourglass.hourglassspring.model.api.json;

import com.ryanlindeborg.hourglass.hourglassspring.model.Industry;
import com.ryanlindeborg.hourglass.hourglassspring.model.JobType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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
}
