package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.DAO.UserDAO;
import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;

public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDAO userdao;
	
	
	public void createUser(UserDetails user){
		userdao.registration(user);
	}
	@Override
	public UserDetails loginUser(UserDetails user) {
		return userdao.login(user);
	}
	@Override
	public UserDetails emailValidation(String email) {
		
		return userdao.emailValidation(email);
	}
	public Token getToken(String token){
		
		return userdao.getToken(token);
	}
	@Override
	public void saveTokenInRedis(Token accessToken) {
		
		userdao.saveTokenInRedis(accessToken);
	}
	
}
