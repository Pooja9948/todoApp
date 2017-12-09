package com.bridgelabz.User.Service;

import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

public interface UserService {

	public int createUser(UserDetails user);

	public UserDetails loginUser(UserDetails user);

	public UserDetails emailValidation(String email);

	public void saveTokenInRedis(Token accessToken);

	public Token getToken(String generateToken);

	public UserDetails getUserById(int id);

	public boolean updateUser(UserDetails userDetails);

	public UserDetails getUserByEmail(String email);

	public void saveOTP(OTPDetails otpDetails);
	
	public UserDetails userValidated(int UserId);
	
	public boolean updateUserPassword(UserDetails userDetails);
	// public void registration(UserDetails userForFb);
}
