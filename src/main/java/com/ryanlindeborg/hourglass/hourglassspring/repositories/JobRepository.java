package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
