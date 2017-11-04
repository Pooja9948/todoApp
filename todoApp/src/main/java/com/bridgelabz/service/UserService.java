package com.bridgelabz.service;

import com.bridgelabz.model.UserDetails;

public interface UserService {
	
	public void createUser(UserDetails user);
	public UserDetails loginUser(UserDetails user);
	public UserDetails emailValidation(String email);
}
