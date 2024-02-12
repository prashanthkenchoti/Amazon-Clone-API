package com.jsp.amazonclone.reposotory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.RefreshToken;
import com.jsp.amazonclone.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String rt);

}
