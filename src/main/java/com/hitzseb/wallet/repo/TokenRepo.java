package com.hitzseb.wallet.repo;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitzseb.wallet.model.Token;

@Repository
@Transactional(readOnly = true)
public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token t"
            + " SET t.confirmedAt = ?2"
            + " WHERE t.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
