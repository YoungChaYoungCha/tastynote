package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    Optional<MemberRefreshToken> findByToken(String token);
}
