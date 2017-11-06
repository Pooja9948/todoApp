package com.bridgelabz.DAO;

import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;

public interface UserDAO {
	public void registration(UserDetails userDetails);
	public UserDetails login(UserDetails userDetails);
	public UserDetails emailValidation(String email);
	public void saveTokenInRedis(Token token);
	public Token getToken(String token);
}
