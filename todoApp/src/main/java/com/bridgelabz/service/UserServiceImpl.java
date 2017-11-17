package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.DAO.UserDAO;
import com.bridgelabz.model.OTPDetails;
import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userdao;

	public int createUser(UserDetails user) {
		return userdao.registration(user);
	}

	@Override
	public UserDetails loginUser(UserDetails user) {
		return userdao.login(user);
	}

	@Override
	public UserDetails emailValidation(String email) {

		return userdao.emailValidation(email);
	}

	public Token getToken(String token) {

		return userdao.getToken(token);
	}

	@Override
	public void saveTokenInRedis(Token accessToken) {

		userdao.saveTokenInRedis(accessToken);
	}

	public UserDetails getUserById(int id) {

		return userdao.getUserById(id);
	}

	public boolean updateUser(UserDetails user) {

		return userdao.updateUser(user);
	}

	@Override
	public UserDetails getUserByEmail(String email) {

		return userdao.getUserByEmail(email);
	}

	@Override
	public void saveOTP(OTPDetails otpDetails) {

		userdao.saveOTP(otpDetails);
	}

}
