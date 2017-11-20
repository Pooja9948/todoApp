package com.bridgelabz.User.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * @author Pooja
 *
 */
@RestController
public class UserController {

	public  static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userservice;

	@Autowired
	Validator validator;

	@Autowired
	SendMail sendmail;

	@Autowired
	PasswordEncryption encryption;

	@RequestMapping(value = "/registrationForm", method = RequestMethod.POST)
	public ResponseEntity<Response> registrationUser(@RequestBody UserDetails user, HttpServletRequest request) {
		CustomResponse customResponse = new CustomResponse();
		logger.trace(user);
		String isValidate = validator.validateSaveUser(user);
		//System.out.println("check valid" + isValidate);
		if (isValidate.equals("")) {
			System.out.println("is validte " + isValidate);
			user.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
			System.out.println("encrypted password : " + user.getPassword());
			int id = userservice.createUser(user);
			if (id != 0) {
				String activeToken = GenerateToken.generateToken(id);
				//System.out.println("jgdfqew   :" + activeToken);
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/" + "verifyMail/" + activeToken;
				try {
					sendmail.sendMail("om4java@gmail.com", user.getEmail(), "Welcome to bridgelabz", url);
					customResponse.setMessage(isValidate);
					return new ResponseEntity<Response>(customResponse,HttpStatus.OK);
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
		return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);

	}

	@RequestMapping(value = "/verifyMail/{activeToken:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyMail(@PathVariable("activeToken") String activeToken,
			HttpServletResponse response) throws IOException {
		CustomResponse customResponse = new CustomResponse();
		
		System.out.println("active token : " + activeToken);
		UserDetails userDetails = null;
		int id = VerifyToken.verifyAccessToken(activeToken);
		System.out.println("id :" + id);
		try {
			userDetails = userservice.getUserById(id);
			System.out.println("User details : " + userDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userDetails.setActivated(true);
		try {
			userservice.updateUser(userDetails);
		}catch (Exception e) {
			e.printStackTrace();
		}
		customResponse.setStatus(200);
		customResponse.setMessage("user Email id verified successfully now plzz login....");
		response.sendRedirect("http://localhost:8080/todoApp/#!/login");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> loginUser(@RequestBody UserDetails user,
			HttpSession session) {
		CustomResponse customResponse = new CustomResponse();
		//System.out.println("email " + user.getEmail() + " password " + user.getPassword());
		user.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
		System.out.println("at the time of login : " + PasswordEncryption.encryptedPassword(user.getPassword()));
		user = userservice.loginUser(user);
		session.setAttribute("user", user);

		if (user != null) {
			String accessToken = GenerateToken.generateToken(user.getId());
			System.out.println("token " + accessToken);
			session.setAttribute("user", user);
			session.setAttribute("token", accessToken);
			
			//token.setGenerateToken(accessToken);
			//String url = request.getRequestURL().toString();
			//url = url.substring(0, url.lastIndexOf("/")) + "/" + "finalLogin" + "/" + accessToken;
			//System.out.println("url : " + url);
			//userservice.saveTokenInRedis(token);
			customResponse.setMessage(accessToken);
			System.out.println("login successful!!!");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("login unsuccessful!!!");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	/*@RequestMapping("/finalLogin/{token}")
	public ResponseEntity<String> checkValidUser(@PathVariable("token") String generateToken) {
		CustomResponse customResponse = new CustomResponse();
		Token token = userservice.getToken(generateToken);
		if (token == null) {

			return new ResponseEntity<String>("token is incorrect", HttpStatus.BAD_REQUEST);
		}
		if (token.getGenerateToken().equals(generateToken)) {

			return new ResponseEntity<String>("successfull login", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Unsuccessfull login", HttpStatus.BAD_REQUEST);
	}*/

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(HttpSession session) {
		CustomResponse customResponse = new CustomResponse();
		session.removeAttribute("user");
		session.invalidate();
		customResponse.setMessage("Logout seccessful!!!");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public Response forgotPassword(@RequestBody UserDetails user, HttpServletRequest request, HttpSession session) {
		System.out.println(user.getEmail());
		UserDetails userDetails= userservice.getUserByEmail(user.getEmail());
		CustomResponse customResponse = new CustomResponse();
		String url = request.getRequestURL().toString();
		int lastIndex = url.lastIndexOf("/");
		String urlofForgotPassword = url.substring(0, lastIndex) + "/otp";
		user = userservice.emailValidation(user.getEmail());
		if (user == null) {
			customResponse.setMessage("Please enter valid emailID");
			customResponse.setStatus(500);
			return customResponse;
		}
		try {
			//String generateOTP = GenerateOTP.generateToken(user.getId());
			String generateOTP = GenerateOTP.generateOTP();
			//OTPDetails otpDetails=new OTPDetails();
			//otpDetails.setOtp(generateOTP);
			//otpDetails.setUser_id(userDetails.getId());
			//userservice.saveOTP(otpDetails);
			session.setAttribute("OTP", generateOTP);
			sendmail.sendMail("om4java@gmail.com", user.getEmail(), "",
					urlofForgotPassword	 +"  Your OTP is : " + generateOTP);
		} catch (Exception e) {
			e.printStackTrace();
			customResponse.setStatus(400);
			return customResponse;
		}
		customResponse.setMessage("Forget Success");
		customResponse.setStatus(200);
		return customResponse;
	}
	
	@RequestMapping(value = "/otp", method = RequestMethod.POST)
	public Response getOTP(@RequestBody String otp, HttpServletRequest request, HttpSession session) {
		System.out.println("otp is : "+otp);
		String sessionOTP= (String) session.getAttribute("OTP");
		CustomResponse customResponse = new CustomResponse();
		if(sessionOTP==otp){
			customResponse.setMessage("otp matched");
			System.out.println("login successful!!!");
			return customResponse;
		}
		customResponse.setMessage("otp failed");
		customResponse.setStatus(200);
		return customResponse;
	}
	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public Response resetPassword(@RequestBody UserDetails user, HttpSession session) {
		System.out.println("password :" + user.getPassword());
		CustomResponse customResponse = new CustomResponse();
		//String email = user.getEmail();
		//UserDetails userDetails=session.getAttribute("OTP");
		String password = user.getPassword();

		// check validation for password
		String isValidate = validator.validateSaveUser(user);
		System.out.println("check valid" + isValidate);

		// email validation
		//user = userservice.emailValidation(email);
		if (user == null) {
			customResponse.setMessage("User not found :");
			customResponse.setStatus(500);
			return customResponse;
		}
		user.setPassword(PasswordEncryption.encryptedPassword(password));
		if (userservice.updateUser(user) && isValidate.equals("")) {
			customResponse.setMessage("Reset password is success :");
			customResponse.setStatus(200);
			return customResponse;
		} else {
			customResponse.setMessage("Password could not be changed");
			customResponse.setStatus(-200);
			return customResponse;
		}
	}

}
