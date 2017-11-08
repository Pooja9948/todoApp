package com.bridgelabz.service;

import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;

public interface UserService {
	
	public int createUser(UserDetails user);
	public UserDetails loginUser(UserDetails user);
	public UserDetails emailValidation(String email);
	public void saveTokenInRedis(Token accessToken);
	public Token getToken(String generateToken);
	public UserDetails getUserById(int id);
	public boolean updateUser(UserDetails userDetails);
	//public boolean emailValidate(UserDetails userDetails);
}
