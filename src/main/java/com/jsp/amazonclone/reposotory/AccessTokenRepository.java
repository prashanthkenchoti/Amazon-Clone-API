package com.jsp.amazonclone.reposotory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

	Optional<AccessToken> findByToken(String at);

	



}
