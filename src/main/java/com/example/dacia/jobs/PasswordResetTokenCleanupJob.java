package com.example.dacia.jobs;

import com.example.dacia.dao.PasswordResetTokenRepository;
import com.example.dacia.model.entities.PasswordResetToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PasswordResetTokenCleanupJob {

    private final PasswordResetTokenRepository tokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetTokenCleanupJob.class);

    public PasswordResetTokenCleanupJob(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void cleanUpExpiredTokens() {
        logger.info("Cleaning up expired tokens");
        LocalDateTime now = LocalDateTime.now();
        List<PasswordResetToken> expiredTokens = tokenRepository.findByExpiryDateBefore(now);

        for (PasswordResetToken token : expiredTokens) {
                logger.info("Deleting Expired Token: User ID={}, Expiry Date={}",
                        token.getUser().getId(), token.getExpiryDate());
        }

        int deletedCount = tokenRepository.deleteByExpiryDateBefore(now);
        logger.info("Total Expired Tokens Deleted: {}", deletedCount);
    }

}

