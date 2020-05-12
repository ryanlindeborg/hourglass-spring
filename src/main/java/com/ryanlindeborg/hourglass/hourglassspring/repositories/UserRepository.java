package com.ryanlindeborg.hourglass.hourglassspring.repositories;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
}
