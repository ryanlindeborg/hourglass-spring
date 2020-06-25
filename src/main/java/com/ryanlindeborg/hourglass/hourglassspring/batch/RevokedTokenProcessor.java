package com.ryanlindeborg.hourglass.hourglassspring.batch;

import com.ryanlindeborg.hourglass.hourglassspring.model.security.RevokedToken;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.security.RevokedTokenRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class RevokedTokenProcessor implements ItemProcessor<RevokedToken, RevokedToken> {

    @Autowired
    RevokedTokenRepository revokedTokenRepository;

    @Override
    public RevokedToken process(RevokedToken revokedToken) {
        revokedTokenRepository.delete(revokedToken);

        return revokedToken;
    }
}
