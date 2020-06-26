package com.ryanlindeborg.hourglass.hourglassspring.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {

    @RequestMapping("/status")
    // https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Object test() {
        return Collections.singletonMap("msg", "You are an admin");
    }
}
