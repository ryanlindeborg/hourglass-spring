package com.ryanlindeborg.hourglass.hourglassspring.batch;

import com.ryanlindeborg.hourglass.hourglassspring.model.security.RevokedToken;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.security.RevokedTokenRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class RevokedTokenProcessor implements ItemProcessor<RevokedToken, RevokedToken> {

    @Autowired
    RevokedTokenRepository revokedTokenRepository;

    @Override
    public RevokedToken process(RevokedToken revokedToken) {
        // TODO: Move this to other part of batch configuration
//        // Grab list of revoked tokens with expiration date before current date
//        Date currentDate = new Date();
//        List<RevokedToken> expiredRevokedTokens = revokedTokenRepository.getRevokedTokensByExpirationDateBefore(currentDate);

        revokedTokenRepository.delete(revokedToken);

        return revokedToken;
    }
}
