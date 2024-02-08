package com.jsp.amazonclone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(PasswordEncoder());
		return provider;
	}

	@Bean
	 org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder() {
		
		return new BCryptPasswordEncoder(15);
	}
	
	@Bean
	SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		return httpSecurity.csrf(csrf ->csrf.disable())
				.formLogin(org.springframework.security.config.Customizer.withDefaults())
				.authorizeHttpRequests(auth->auth.requestMatchers("/**").permitAll().anyRequest().authenticated())
				.build();
	}


}
