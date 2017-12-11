package com.bridgelabz.Util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * @author Pooja todoApp
 *
 */
public class VerifyToken {
	
	public static final String  key="toDoToken";
	
	/**
	 * @param accessToken
	 * @return
	 * verifying the token
	 */
	public static int verifyAccessToken(String accessToken ) {
		try {
			JwtParser parser = Jwts.parser();
			Claims claims = parser.setSigningKey(key).parseClaimsJws(accessToken).getBody();
			//System.out.println("claims : "claims.getIssuer());
			return Integer.parseInt(claims.getIssuer());
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
