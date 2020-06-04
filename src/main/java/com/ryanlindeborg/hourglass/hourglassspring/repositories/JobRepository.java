package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface JobRepository extends JpaRepository<Job, Long> {
    public List<Job> getJobsByUserId(Long userId);

    public Job getJobByUserIdAndJobType(Long userId, JobType jobType);

    //TODO: Refactor - Find by username or email - do by username and autogenerate username for industry leaders
    // TODO: Can also try to replace query with string; see if problem is with enum type
    @Query("SELECT j FROM Job j WHERE j.user.id = :userId and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.CURRENT_JOB")
    public Job getCurrentJobForUser(@Param("userId") Long userId);
}
