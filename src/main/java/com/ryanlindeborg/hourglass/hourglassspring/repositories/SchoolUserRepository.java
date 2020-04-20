package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.SchoolUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Long> {
    public List<SchoolUser> getSchoolUsersByUserId(Long userId);
}
