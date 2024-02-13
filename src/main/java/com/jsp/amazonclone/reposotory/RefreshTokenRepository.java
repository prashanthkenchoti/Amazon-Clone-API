package com.jsp.amazonclone.reposotory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.RefreshToken;
import com.jsp.amazonclone.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String rt);

	List<RefreshToken> findByExpirationBefore(LocalDateTime now);

	List<RefreshToken> findByTokenAndIsBlocked(User user, boolean b);

	List<RefreshToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String refreshToken);

}
