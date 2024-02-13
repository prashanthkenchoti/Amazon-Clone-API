package com.jsp.amazonclone.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jsp.amazonclone.entity.AccessToken;
import com.jsp.amazonclone.exception.UserNotLoggedInException;
import com.jsp.amazonclone.reposotory.AccessTokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private AccessTokenRepository accessTokenRepository;
	
	private JwtService jwtService;
	
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String at=null;
		String rt=null;
		Cookie[] cookies= request.getCookies();
		if(cookies!=null) {
		for (Cookie cookie : cookies) {
			
			if(cookie.getName().equals("at")) at= cookie.getValue();
			if(cookie.getName().equals("rt")) at= cookie.getValue();
			
		}
		
		String username=null;
		if(at!=null|| rt!=null) {
		
			Optional<AccessToken>	accessToken = accessTokenRepository. findByTokenAndIsBlocked(at,false);
			if(accessToken==null)throw new UserNotLoggedInException();
			username=jwtService.extractUserName(at);
			if(username==null) throw new UserNotLoggedInException("Failed to authenticate user");
			else
			{
				log.info("Authenticating the token....");
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					username, null,userDetails.getAuthorities());
			token.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(token);
			
			log.info("authenticated successfully...");
		}
		}
		}
			filterChain.doFilter(request, response);// passes the req and res to next filters
	}
		
		
	}


