package com.hitzseb.billeterapp.service;

import com.hitzseb.billeterapp.model.Token;
import com.hitzseb.billeterapp.model.User;
import com.hitzseb.billeterapp.repository.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token findByToken(String authToken) throws EntityNotFoundException {
        return tokenRepository.findByToken(authToken).orElseThrow(() -> new EntityNotFoundException("Token not found"));
    }

    public boolean isValid(Token token) {
        return !(token.isExpired() || token.isRevoked());
    }

    public void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
