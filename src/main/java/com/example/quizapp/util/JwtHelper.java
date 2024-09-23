package com.example.quizapp.util;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtHelper {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Value("${token.validity}")
	private long VALIDITY;

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusMillis(VALIDITY))).signWith(generateKey()).compact();
	}

	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	public String extractUsername(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getSubject();
	}

	private Claims getClaims(String jwt) {
		return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
	}

	public boolean isTokenValid(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}
}