package com.ryanlindeborg.hourglass.hourglassspring.repositories.security;

import com.ryanlindeborg.hourglass.hourglassspring.model.security.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    public RevokedToken getRevokedTokenByToken(String token);

    public List<RevokedToken> getRevokedTokensByExpirationDateBefore(Date compareDate);
}
