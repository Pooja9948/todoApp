package com.bridgelabz.model;

import java.io.Serializable;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

@Component
public class Token implements Serializable{
	
	private static final long serialVersionUID = 3650165898539244046L;

	@Column(name="generate_token")
	private String generateToken;
	@Column(name="refresh_token")
	private String refreshToken;
	
	
	
	public String getGenerateToken() {
		return generateToken;
	}

	public void setGenerateToken(String generateToken) {
		this.generateToken = generateToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	

}
