package com.bridgelabz.User.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.User.DAO.UserDAO;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

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
