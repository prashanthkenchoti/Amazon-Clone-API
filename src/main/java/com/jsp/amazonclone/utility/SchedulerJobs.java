package com.jsp.amazonclone.utility;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsp.amazonclone.entity.AccessToken;
import com.jsp.amazonclone.entity.RefreshToken;
import com.jsp.amazonclone.reposotory.AccessTokenRepository;
import com.jsp.amazonclone.reposotory.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SchedulerJobs {
	
	private AccessTokenRepository accessTokenRepo;

	private RefreshTokenRepository refreshTokenRepo;
	
	// So, the expression * * */6 * * * means "run the task every six hours,
	//regardless of the minute, second, day of the month, month, or day of the week."		
	@Scheduled(cron = "* * */6 * * *") 
		public void cleanUpAllTheExpiredToken()
		{
			LocalDateTime now = LocalDateTime.now();
			List<AccessToken> accessTokenList = accessTokenRepo.findByExpirationBefore(now);
			accessTokenRepo.deleteAll(accessTokenList);
			
			List<RefreshToken> refreshTokenList = refreshTokenRepo.findByExpirationBefore(now);
			refreshTokenRepo.deleteAll(refreshTokenList);

		}

}
