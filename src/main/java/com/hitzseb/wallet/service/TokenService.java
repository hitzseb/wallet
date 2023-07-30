package com.hitzseb.wallet.service;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hitzseb.wallet.model.Token;
import com.hitzseb.wallet.repo.TokenRepo;

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
