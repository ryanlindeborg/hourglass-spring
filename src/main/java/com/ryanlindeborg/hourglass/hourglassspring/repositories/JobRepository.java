package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.Job;
import com.ryanlindeborg.hourglass.hourglassspring.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

// Note: Have to rename this jobRepository because it conflicts with Spring Batch jobRepository
@Repository("hourglassJobRepository")
@Transactional
public interface JobRepository extends JpaRepository<Job, Long> {
    public List<Job> getJobsByUserId(Long userId);

    @Query("SELECT j FROM Job j WHERE j.user.id = :userId and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.CURRENT_JOB")
    public Job getCurrentJobForUserByUserId(@Param("userId") Long userId);

    @Query("SELECT j FROM Job j WHERE j.user.id = :userId and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.FIRST_POST_COLLEGE_JOB")
    public Job getFirstPostCollegeJobForUserByUserId(@Param("userId") Long userId);

    @Query("SELECT j FROM Job j WHERE j.user.id = :userId and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.DREAM_JOB")
    public Job getDreamJobForUserByUserId(@Param("userId") Long userId);
}
