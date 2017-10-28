package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.DAO.BankDAO;
import com.bridgelabz.model.UserDetails;

public class UserServiceImpl implements UserService{
	
	@Autowired
	BankDAO bankdao;
	public void createUser(UserDetails user){
		bankdao.registration(user);
	}
}
