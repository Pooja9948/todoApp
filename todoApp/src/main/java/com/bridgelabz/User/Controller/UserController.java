package com.bridgelabz.User.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.User.Service.UserService;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.GenerateOTP;
import com.bridgelabz.Util.PasswordEncryption;
import com.bridgelabz.Util.SendMail;
import com.bridgelabz.Util.response.CustomResponse;
import com.bridgelabz.Util.response.Response;
import com.bridgelabz.Util.token.GenerateToken;
import com.bridgelabz.Util.token.VerifyToken;
import com.bridgelabz.Util.validation.Validator;

/**
 * @author Pooja todoApp
 *
 */
@RestController
public class UserController {

	public static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userservice;

	@Autowired
	Validator validator;

	@Autowired
	SendMail sendmail;

	@Autowired
	PasswordEncryption encryption;

	/**
	 * @param user
	 * @param request
	 * @return
	 * get all the data from the request of the registration form and and save it
	 */
	@RequestMapping(value = "/registrationForm", method = RequestMethod.POST)
	public ResponseEntity<Response> registrationUser(@RequestBody UserDetails user, HttpServletRequest request) {
		CustomResponse customResponse = new CustomResponse();
		logger.trace(user);
		String isValidate = validator.validateSaveUser(user);
		// System.out.println("check valid" + isValidate);
		if (isValidate.equals("")) {
			//System.out.println("is validte " + isValidate);
			user.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
			//System.out.println("encrypted password : " + user.getPassword());
			int id = userservice.createUser(user);
			if (id != 0) {
				String activeToken = GenerateToken.generateToken(id);
				// System.out.println("jgdfqew :" + activeToken);
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/" + "verifyMail/" + activeToken;
				try {
					sendmail.sendMail("om4java@gmail.com", user.getEmail(), "Welcome to bridgelabz", url);
					customResponse.setMessage(isValidate);
					return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
				} catch (MailException e) {

					e.printStackTrace();
				}
			}
			// sendmail.sendMail("om4java@gmail.com", user.getEmail(),
			// "welcome", "Registration successful");
			// return new ResponseEntity<String>(HttpStatus.OK);
		}
		Response response = new Response();
		response.setMessage(isValidate);
		return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);

	}

	/**
	 * @param activeToken
	 * @param response
	 * @return
	 * @throws IOException
	 * after registration , one mail will go to the user's mailid to activate the account
	 */
	@RequestMapping(value = "/verifyMail/{activeToken:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyMail(@PathVariable("activeToken") String activeToken,
			HttpServletResponse response) throws IOException {
		CustomResponse customResponse = new CustomResponse();

		//System.out.println("active token : " + activeToken);
		UserDetails userDetails = null;
		int id = VerifyToken.verifyAccessToken(activeToken);
		//System.out.println("id :" + id);
		try {
			userDetails = userservice.getUserById(id);
			//System.out.println("User details : " + userDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userDetails.setActivated(true);
		try {
			userservice.updateUser(userDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customResponse.setStatus(200);
		customResponse.setMessage("user Email id verified successfully now plzz login....");
		response.sendRedirect("http://localhost:8080/todoApp/#!/login");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	/**
	 * @param user
	 * @param session
	 * @return
	 * get the username, password , check that those are valid or not. If it is valid then user should go the home page otherwise stay in the login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> loginUser(@RequestBody UserDetails user, HttpSession session) {
		CustomResponse customResponse = new CustomResponse();
		// System.out.println("email " + user.getEmail() + " password " +
		// user.getPassword());
		user.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
		//System.out.println("at the time of login : " + PasswordEncryption.encryptedPassword(user.getPassword()));
		user = userservice.loginUser(user);

		if (user != null) {
			session.setAttribute("user", user);
			// System.out.println("user.getId() ->>"+user.getId());
			String accessToken = GenerateToken.generateToken(user.getId());

			customResponse.setMessage(accessToken);
			//System.out.println("login successful!!!");
			return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		} else {
			//System.out.println("login unsuccessful!!!");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * @param session
	 * @return
	 * logout the user and clear the session
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(HttpSession session) {
		CustomResponse customResponse = new CustomResponse();
		session.removeAttribute("user");
		session.invalidate();
		customResponse.setMessage("Logout seccessful!!!");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	/**
	 * @param user
	 * @param request
	 * @param session
	 * @return
	 * for forgot password , enter mailid to send the reset page link with token
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public Response forgotPassword(@RequestBody UserDetails user, HttpServletRequest request, HttpSession session) {
		//System.out.println(user.getEmail());
		UserDetails userDetails = userservice.getUserByEmail(user.getEmail());
		CustomResponse customResponse = new CustomResponse();
		String activeToken = GenerateToken.generateToken(userDetails.getId());
		session.setAttribute("resetToken", activeToken);
		int id = VerifyToken.verifyAccessToken(activeToken);
		String url = request.getRequestURL().toString();
		int lastIndex = url.lastIndexOf("/");
		String urlofForgotPassword = url.substring(0, lastIndex) + "/#!/resetpassword ";
		user = userservice.emailValidation(user.getEmail());
		if (user == null) {
			customResponse.setMessage("Please enter valid emailID");
			customResponse.setStatus(500);
			return customResponse;
		}
		try {
			sendmail.sendMail("om4java@gmail.com", user.getEmail(), "Fundoo Keep Forgot Password Link",
					"Please click here to set reset password . "+urlofForgotPassword);
		} catch (Exception e) {
			e.printStackTrace();
			customResponse.setStatus(400);
			return customResponse;
		}
		customResponse.setMessage("Forget Success");
		customResponse.setStatus(200);
		return customResponse;
	}
	
	/**
	 * @param user
	 * @param session
	 * @return
	 * reset password of the user by the userId
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public Response resetPassword(@RequestBody UserDetails user,HttpSession session) {
		
		CustomResponse customResponse = new CustomResponse();
		String token=(String) session.getAttribute("resetToken");
		int userId = VerifyToken.verifyAccessToken(token);
		if (user == null) {
			customResponse.setMessage("User not found :");
			customResponse.setStatus(500);
			return customResponse;
		}
		UserDetails userDetails=new UserDetails();
		userDetails.setId(userId);
		userDetails.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
		boolean ret=userservice.updateUserPassword(userDetails);
		if (ret!=false) {
			customResponse.setMessage("Reset password is success :");
			customResponse.setStatus(200);
			return customResponse;
		} else {
			customResponse.setMessage("Password could not be changed");
			customResponse.setStatus(-200);
			return customResponse;
		}
	}

	/**
	 * @param request
	 * @return
	 * get the information about the current user
	 */
	@RequestMapping(value = "/user/currentUser", method = RequestMethod.GET)
	public UserDetails currrentUser(HttpServletRequest request) {
		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		//System.out.println("*********** " + id);
		UserDetails user = userservice.getUserById(id);
		if (user != null) {
			customResponse.setMessage("User found :");
			return user;
		}
		return user;
	}

	/**
	 * @param user
	 * @param request
	 * @return
	 * @throws IOException
	 * change the profile of the current user
	 */
	@RequestMapping(value = "/user/profileChange", method = RequestMethod.POST)
	public ResponseEntity<String> changeProfile(@RequestBody UserDetails user, HttpServletRequest request)
			throws IOException {
		//System.out.println("inside profilechange....");
		int userid = (int) request.getAttribute("userId");
		if (userid == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		}
		userservice.updateUser(user);
		return ResponseEntity.ok("");
	}
	
	/**
	 * @param session
	 * @return
	 * get the access token by the time of login
	 */
	@RequestMapping(value="/social")
	public ResponseEntity<Response> getAccessTokenByglogin(HttpSession session){
		String acessToken = (String) session.getAttribute("myAccessToken");
		
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage(acessToken);
		return ResponseEntity.ok(customResponse);

	}

}
