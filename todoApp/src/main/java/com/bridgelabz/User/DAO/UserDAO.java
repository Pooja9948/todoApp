package com.bridgelabz.User.DAO;

import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

/**
 * @author Pooja todoApp
 *
 */
public interface UserDAO {
	
	/**
	 * @param userDetails
	 * @return
	 * register the user by userDetails object
	 */
	public int registration(UserDetails userDetails);

	/**
	 * @param userDetails
	 * @return
	 * check the login user valid or not at the login time
	 */
	public UserDetails login(UserDetails userDetails);

	/**
	 * @param email
	 * @return
	 * check the email id is already exist or not
	 */
	public UserDetails emailValidation(String email);

	/**
	 * @param token
	 * save token in redis
	 */
	public void saveTokenInRedis(Token token);

	/**
	 * @param token
	 * @return
	 * get token from redis
	 */
	public Token getToken(String token);

	/**
	 * @param id
	 * @return
	 * get user by id
	 */
	public UserDetails getUserById(int id);

	/**
	 * @param user
	 * @return
	 * update user
	 */
	public boolean updateUser(UserDetails user);

	/**
	 * @param email
	 * @return 
	 * get user by email id
	 */
	public UserDetails getUserByEmail(String email);

	/**
	 * @param otpDetails
	 * save otp
	 */
	public void saveOTP(OTPDetails otpDetails);
	
	/**
	 * @param UserId
	 * @return
	 * check the user is valid or not
	 */
	public UserDetails userValidated(int UserId);
	
	/**
	 * @param userDetails
	 * @return
	 * update the user password
	 */
	public boolean updateUserPassword(UserDetails userDetails);

}
