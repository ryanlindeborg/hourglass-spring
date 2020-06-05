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

    public SchoolUser getSchoolUserByUserIdAndSchoolUserType(Long userId, SchoolUserType schoolUserType);

    public List<SchoolUser> getSchoolUsersByUserDisplayName(String displayName);

    public SchoolUser getSchoolUserByUserDisplayNameAndSchoolUserType(String displayName, SchoolUserType schoolUserType);

    @Query("SELECT su FROM SchoolUser su WHERE su.user.displayName = :displayName and su.schoolUserType = com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUserType.COLLEGE")
    public SchoolUser getCollegeSchoolUserByUserDisplayName(@Param("displayName") String displayName);

    @Query("SELECT su FROM SchoolUser su WHERE su.user.displayName = :displayName and su.schoolUserType = com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUserType.POST_GRAD")
    public SchoolUser getPostGradSchoolUserByUserDisplayName(@Param("displayName") String displayName);
}
