package com.example.dacia.jobs;

import com.example.dacia.dao.PasswordResetTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class PasswordResetTokenCleanupJob {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetTokenCleanupJob(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void cleanUpExpiredTokens() {
        int deletedCount = tokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
        System.out.println("Deleted " + deletedCount + " expired tokens.");
    }
}

