package com.jsp.amazonclone.reposotory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.AccessToken;
import com.jsp.amazonclone.entity.User;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

	AccessToken findByToken(String at);
	


	List<AccessToken> findByExpirationBefore(LocalDateTime now);

	Optional<AccessToken> findByTokenAndIsBlocked(String at, boolean b);

	List<AccessToken>	findAllByUserAndIsBlocked(User user, boolean b);

	List<AccessToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String accessToken);

	
	



}
