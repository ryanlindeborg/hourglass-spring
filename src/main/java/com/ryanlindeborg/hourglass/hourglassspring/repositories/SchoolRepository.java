package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
