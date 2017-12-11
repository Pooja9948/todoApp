package com.bridgelabz.User.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.User.DAO.UserDAO;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.OTPDetails;
import com.bridgelabz.Util.token.Token;

/**
 * @author Pooja todoApp
 *
 */
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userdao;

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#createUser(com.bridgelabz.User.model.UserDetails)
	 * crate the user and return the no of rows inserted
	 */
	public int createUser(UserDetails user) {
		return userdao.registration(user);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#loginUser(com.bridgelabz.User.model.UserDetails)
	 * check the user is valid or not
	 */
	@Override
	public UserDetails loginUser(UserDetails user) {
		return userdao.login(user);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#emailValidation(java.lang.String)
	 * check the email is valid or not
	 */
	@Override
	public UserDetails emailValidation(String email) {

		return userdao.emailValidation(email);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#getToken(java.lang.String)
	 * get the token from redis
	 */
	public Token getToken(String token) {

		return userdao.getToken(token);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#saveTokenInRedis(com.bridgelabz.Util.token.Token)
	 * save the token in redis
	 */
	@Override
	public void saveTokenInRedis(Token accessToken) {

		userdao.saveTokenInRedis(accessToken);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#getUserById(int)
	 * get the user by id
	 */
	public UserDetails getUserById(int id) {

		return userdao.getUserById(id);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#updateUser(com.bridgelabz.User.model.UserDetails)
	 * update the user
	 */
	public boolean updateUser(UserDetails user) {

		return userdao.updateUser(user);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#getUserByEmail(java.lang.String)
	 * get the user by email
	 */
	@Override
	public UserDetails getUserByEmail(String email) {

		return userdao.getUserByEmail(email);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#saveOTP(com.bridgelabz.Util.OTPDetails)
	 * save the otp
	 */
	@Override
	public void saveOTP(OTPDetails otpDetails) {

		userdao.saveOTP(otpDetails);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#userValidated(int)
	 * check the user validate or not
	 */
	public UserDetails userValidated(int UserId) {

		return userdao.userValidated(UserId);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.User.Service.UserService#updateUserPassword(com.bridgelabz.User.model.UserDetails)
	 * update the user's password
	 */
	@Override
	public boolean updateUserPassword(UserDetails userDetails) {
		
		return userdao.updateUserPassword(userDetails);
	}
}
