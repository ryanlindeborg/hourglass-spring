package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Long> {
    public List<SchoolUser> getSchoolUsersByUserId(Long userId);

    @Query("SELECT su FROM SchoolUser su WHERE su.user.id = :userId and su.schoolUserType = com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUserType.COLLEGE")
    public SchoolUser getCollegeSchoolUserByUserId(@Param("userId") Long userId);

    @Query("SELECT su FROM SchoolUser su WHERE su.user.id = :userId and su.schoolUserType = com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUserType.POST_GRAD")
    public SchoolUser getPostGradSchoolUserByUserId(@Param("userId") Long userId);

    @Query("SELECT su FROM SchoolUser su WHERE su.school.name = :schoolName")
    public List<SchoolUser> getSchoolUsersBySchoolName(@Param("schoolName") String schoolName);
}
