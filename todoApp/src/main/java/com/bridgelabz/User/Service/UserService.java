package com.bridgelabz.User.Service;

import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

/**
 * @author Pooja todoApp
 *
 */
public interface UserService {

	/**
	 * @param user
	 * @return
	 * create user
	 */
	public int createUser(UserDetails user);

	/**
	 * @param user
	 * @return
	 * check user is valid or not at the time of login
	 */
	public UserDetails loginUser(UserDetails user);

	/**
	 * @param email
	 * @return
	 * check the mail id is already exist or not
	 */
	public UserDetails emailValidation(String email);

	/**
	 * @param accessToken
	 * save the token in redis
	 */
	public void saveTokenInRedis(Token accessToken);

	/**
	 * @param generateToken
	 * @return
	 * get the token in redis
	 */
	public Token getToken(String generateToken);

	/**
	 * @param id
	 * @return
	 * get the user by Id
	 */
	public UserDetails getUserById(int id);

	/**
	 * @param userDetails
	 * @return
	 * update the user
	 */
	public boolean updateUser(UserDetails userDetails);

	/**
	 * @param email
	 * @return
	 * get the user by email id
	 */
	public UserDetails getUserByEmail(String email);

	/**
	 * @param otpDetails
	 * save the otp
	 */
	public void saveOTP(OTPDetails otpDetails);
	
	/**
	 * @param UserId
	 * @return
	 * check the user is validate or not
	 */
	public UserDetails userValidated(int UserId);
	
	/**
	 * @param userDetails
	 * @return
	 * update the password
	 */
	public boolean updateUserPassword(UserDetails userDetails);
}
