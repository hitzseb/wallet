package com.hitzseb.walletwizardapi.service;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hitzseb.walletwizardapi.model.Token;
import com.hitzseb.walletwizardapi.repo.TokenRepo;

@Service
@AllArgsConstructor
public class TokenService {
	private final TokenRepo tokenRepo;

	public void saveConfirmationToken(Token token) {
		tokenRepo.save(token);
	}

	public Optional<Token> getToken(String token) {
		return tokenRepo.findByToken(token);
	}

	public int setConfirmedAt(String token) {
		return tokenRepo.updateConfirmedAt(token, LocalDateTime.now());
	}
}
