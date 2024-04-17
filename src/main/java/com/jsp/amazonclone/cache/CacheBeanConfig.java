package com.jsp.amazonclone.cache;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jsp.amazonclone.entity.User;
@Configuration
public class CacheBeanConfig {
	
	@Bean
	public CacheStored<User> userCacheStored()
	{
		return new CacheStored<User>(Duration.ofMinutes(5));// setting the time limit for cache memory objects.
	}

	
	@Bean
	public CacheStored<String> otpCacheStore()
	{
		return new CacheStored<String>(Duration.ofMinutes(5));
	}
}
