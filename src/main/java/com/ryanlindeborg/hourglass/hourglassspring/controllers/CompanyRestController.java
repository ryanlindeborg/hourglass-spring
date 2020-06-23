package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.Industry;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.CompanyRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//TODO: Update api/v1 to be api url property so don't have to update this when update api version
@RestController
@RequestMapping(path="/api/v1/company", produces="application/json")
public class CompanyRestController {
    private CompanyRepository companyRepository;

    public CompanyRestController(CompanyRepository companyRepository) { this.companyRepository = companyRepository; }

    @GetMapping("/industries")
    public List<String> getIndustryNames() {
        List<String> industryNames = new ArrayList<>();
        for (Industry industry : Industry.values()) {
            industryNames.add(industry.toString());
        }
        return industryNames;
    }
}
