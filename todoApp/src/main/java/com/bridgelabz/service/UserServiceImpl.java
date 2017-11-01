package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.DAO.UserDAO;
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
	public boolean emailValidation(String email) {
		
		return userdao.emailValidation(email);
	}
	
	

}
