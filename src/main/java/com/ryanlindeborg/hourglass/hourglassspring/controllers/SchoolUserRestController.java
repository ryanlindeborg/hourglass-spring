package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import com.ryanlindeborg.hourglass.hourglassspring.model.Degree;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/schoolUser", produces="application/json")
public class SchoolUserRestController {
    @GetMapping("/degrees")
    public List<String> getDegreeNames() {
        List<String> degreeNames = new ArrayList<>();
        for (Degree degree : Degree.values()) {
            degreeNames.add(degree.toString());
        }
        return degreeNames;
    }
}
