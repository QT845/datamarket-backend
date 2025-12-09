package com.datamarket.backend.repository;

import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);
    void deleteByUser(User user);
    void deleteByExpiredAtBefore(LocalDateTime dateTime);
    void deleteByRefreshToken(String token);

}
