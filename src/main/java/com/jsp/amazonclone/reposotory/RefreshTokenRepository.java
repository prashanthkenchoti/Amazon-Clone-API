package com.jsp.amazonclone.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
