package com.bridgelabz.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class VerifyToken {
	
	public static final String  key="toDoToken";
	
	public static int verifyAccessToken(String accessToken ) {
		try {
			JwtParser parser = Jwts.parser();
			Claims claims = parser.setSigningKey(key).parseClaimsJws(accessToken).getBody();
			return Integer.parseInt(claims.getIssuer());
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
