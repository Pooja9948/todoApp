package com.bridgelabz.token;

import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateToken {
	
	private static final String key="todoToken";
	
	public static String generateToken(int userId){
		Date tokenCreationTime = new Date();
		Date expirationTime = new Date(tokenCreationTime.getTime()+1000*60*5);
		SignatureAlgorithm signatureAlogirthm = SignatureAlgorithm.HS512;
		JwtBuilder builder = Jwts.builder();
		builder.setSubject("accessToken");
		builder.setIssuedAt(tokenCreationTime);
		builder.setExpiration(expirationTime);
		builder.setIssuer(String.valueOf(userId));
		builder.signWith(signatureAlogirthm, key);
		String jwtBuilder = builder.compact();
		return jwtBuilder;
	}
}
