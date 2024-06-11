package com.ram.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

//	providing Jwt token Expiration validity
	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	
	private String secretKey="JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108JaiShreeRam108";
	
//	retrive username from token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
		
	}
	
//	Retrive expirationDate From token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

//	get clam from token
	private <T> T getClaimFromToken(String token, Function<Claims, T>claimFunction) {
		final Claims claims= getAllClaimsFromToken(token);
		return claimFunction.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
//	check if token is expired
	private Boolean isTokenExpired(String token) {
		Date expirationDateFromToken = getExpirationDateFromToken(token);
		return expirationDateFromToken.before(new Date());
	}
	
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
				return doGenerateToken(claims,userDetails.getUsername());
	}
	
	
	@SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}
	
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	

	
	
	
}
