package com.jsp.amazonclone.security;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${myapp.secret}")
	private String secret;
	
	@Value("${myapp.access.expiry}")
	private Long accessExpirationInSeconds;
	
	@Value("${myapp.refresh.expiry}")
	private Long refreshExpirationOInSeconds;
	
	public String generateAccessToken(String userName)
	{
		return generateJWT( new HashMap<String,Object>() , userName,accessExpirationInSeconds*1000l);
	}
	
	private String generateJWT( Map<String,Object> claims ,String userName,Long expiry)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiry))
				.signWith(getSignature(),SignatureAlgorithm.HS512)//signing the jwt with key
				.compact();//it is a method in JwtBuilder interface , it returns the String type of object
				
	}
	
	private Key getSignature()
	{
		byte[] secretBytes=Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
	
	
	public Claims jwtParser(String token) {
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSignature()).build();
		return jwtParser.parseClaimsJws(token).getBody();
	}
	
	public String extractUserName(String token) {
		return jwtParser(token).getSubject();

	}
}
