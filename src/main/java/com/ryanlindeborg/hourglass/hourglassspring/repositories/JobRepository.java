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

    public List<Job> getJobsByUserDisplayName(String displayName);

    public Job getJobByUserDisplayNameAndJobType(String displayNae, JobType jobType);

    @Query("SELECT j FROM Job j WHERE j.user.displayName = :displayName and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.CURRENT_JOB")
    public Job getCurrentJobForUserByDisplayName(@Param("displayName") String displayName);

    @Query("SELECT j FROM Job j WHERE j.user.displayName = :displayName and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.FIRST_POST_COLLEGE_JOB")
    public Job getFirstPostCollegeJobForUserByDisplayName(@Param("displayName") String displayName);

    @Query("SELECT j FROM Job j WHERE j.user.displayName = :displayName and j.jobType = com.ryanlindeborg.hourglass.hourglassspring.model.JobType.DREAM_JOB")
    public Job getDreamJobForUserByDisplayName(@Param("displayName") String displayName);
}
