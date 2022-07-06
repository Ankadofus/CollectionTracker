package com.CollectionTracker.user.password.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService{

	private final PasswordResetTokenRepository passwordTokenRepo;
	
	public PasswordResetTokenService(PasswordResetTokenRepository confirmationTokenRepo) {
		this.passwordTokenRepo = confirmationTokenRepo;
	}
	
	public void savePasswordResetToken(PasswordResetToken token) {
        passwordTokenRepo.save(token);
    }

    public Optional<PasswordResetToken> getToken(String token) {
        return passwordTokenRepo.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return passwordTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }
}

