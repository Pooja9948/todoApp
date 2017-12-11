package com.bridgelabz.Util.token;

import java.io.Serializable;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

/**
 * @author Pooja todoApp
 *
 */
@Component
public class Token implements Serializable{
	
	private static final long serialVersionUID = 3650165898539244046L;

	@Column(name="generate_token")
	private String generateToken;
	@Column(name="refresh_token")
	private String refreshToken;
	
	
	
	/**
	 * @return generateToken
	 */
	public String getGenerateToken() {
		return generateToken;
	}

	/**
	 * @param generateToken
	 */
	public void setGenerateToken(String generateToken) {
		this.generateToken = generateToken;
	}

	/**
	 * @return refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	

}
