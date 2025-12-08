package com.datamarket.backend.repository;

import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    List<RefreshToken> findAllByUserAndRevokedFalse(User user);
    void deleteByExpiredAtBefore(LocalDateTime now);
}
