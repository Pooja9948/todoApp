package com.bridgelabz.User.DAO;

import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

public interface UserDAO {
	public int registration(UserDetails userDetails);

	public UserDetails login(UserDetails userDetails);

	public UserDetails emailValidation(String email);

	public void saveTokenInRedis(Token token);

	public Token getToken(String token);

	public UserDetails getUserById(int id);

	public boolean updateUser(UserDetails user);

	public UserDetails getUserByEmail(String email);

	public void saveOTP(OTPDetails otpDetails);

	// public boolean emailValidate(String email);
	// boolean emailValidate(UserDetails userDetails);
}
