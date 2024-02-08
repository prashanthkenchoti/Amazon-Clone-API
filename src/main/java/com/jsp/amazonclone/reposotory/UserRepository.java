package com.jsp.amazonclone.reposotory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	Optional<User> findByUserName(String userName);

}
