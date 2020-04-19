package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolUserRepository extends JpaRepository<SchoolUser, Long> {
}
