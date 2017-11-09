package com.bridgelabz.DAO;

import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;

public interface UserDAO {
	public int registration(UserDetails userDetails);
	public UserDetails login(UserDetails userDetails);
	public UserDetails emailValidation(String email);
	public void saveTokenInRedis(Token token);
	public Token getToken(String token);
	public UserDetails getUserById(int id);
	public boolean updateUser(UserDetails user);
	public UserDetails getUserByEmail(String email);
	
	//public boolean emailValidate(String email);
	//boolean emailValidate(UserDetails userDetails);
}
