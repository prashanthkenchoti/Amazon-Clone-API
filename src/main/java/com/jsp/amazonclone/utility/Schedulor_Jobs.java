package com.jsp.amazonclone.utility;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsp.amazonclone.reposotory.UserRepository;
import com.jsp.amazonclone.service.AuthService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Schedulor_Jobs {
	
	UserRepository userRepository;
	
	private AuthService authService;

	@Scheduled(cron = "0 0 0 * * *") // Daily at midnight
	public void deleteNonVerifiedUsers()
	{
	   
		userRepository.findByIsEmailVerified(false).forEach((user)->userRepository.delete(user));
	}

}
