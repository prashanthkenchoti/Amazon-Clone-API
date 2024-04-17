package com.jsp.amazonclone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
	

	private CustomUserDetailsService customUserDetailsService;
	
	private JwtFilter jwtFilter;
	
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
		
		return new BCryptPasswordEncoder(15);// in this code it performs 2^15 iterations to generate hashcode
	}
	
	@Bean
	SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		return httpSecurity.csrf(csrf ->csrf.disable())
				//.formLogin(org.springframework.security.config.Customizer.withDefaults()) formlogin we are not using 
				.httpBasic(Customizer.withDefaults())// it is less secure
				.authorizeHttpRequests(auth->auth.requestMatchers("/**").permitAll().anyRequest().authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}


}
