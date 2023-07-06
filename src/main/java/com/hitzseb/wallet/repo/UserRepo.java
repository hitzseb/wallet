package com.hitzseb.wallet.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitzseb.wallet.model.User;

@Repository
@Transactional(readOnly = true)
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u "
            + "SET u.enabled = TRUE"
            + " WHERE u.email = ?1")
    int enableUser(String email);
}
