package com.bridgelabz.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Mail.SendMail;
import com.bridgelabz.model.ErrorMessage;
import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;
import com.bridgelabz.token.GenerateToken;
import com.bridgelabz.token.VerifyToken;
import com.bridgelabz.validation.Validator;

/**
 * @author Pooja for todoApp User Controller
 *
 */

@RestController
public class UserController{

	@Autowired
	UserService userservice;

	@Autowired
	Validator validator;

	@Autowired
	SendMail  sendmail;

	@Autowired
	Token token;

	@Autowired
	ErrorMessage message;

	@RequestMapping(value="/registrationForm", method= RequestMethod.POST)
	public ResponseEntity<String> registrationUser(@RequestBody UserDetails user,HttpServletRequest request) {
		String isValidate = validator.validateSaveUser(user);
		System.out.println("check valid"+isValidate);
		if (isValidate.equals("")) {
			System.out.println("is validte "+isValidate);
			int id=userservice.createUser(user);
			if (id != 0) {
				String activeToken = GenerateToken.generateToken(id);
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/" + "verifyMail/" + activeToken;
				try {
					sendmail.sendMail("om4java@gmail.com", user.getEmail(), "Welcome to bridgelabz", url);
					return new ResponseEntity<String>( HttpStatus.OK);
				} catch (MailException e) {

					e.printStackTrace();
				}
			}
			//sendmail.sendMail("om4java@gmail.com", user.getEmail(), "welcome", "Registration successful");
			//return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.CONFLICT);

	}

	@RequestMapping(value = "/verifyMail/{activeToken:.+}", method = RequestMethod.GET)
	public ResponseEntity<ErrorMessage> verifyMail(@PathVariable("activeToken") String activeToken ,HttpServletResponse response) throws IOException
	{
		UserDetails userDetails=null;
		int id = VerifyToken.verifyAccessToken(activeToken);
		try {
			userDetails =userservice.getUserById(id);
			System.out.println("User details : "+userDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userDetails.setActivated(true);
		try {
			userservice.updateUser(userDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		message.setStatus(200);
		message.setMessage("user Email id verified successfully now plzz login....");
		response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
		return new ResponseEntity<ErrorMessage>(message,HttpStatus.OK);
	}


	@RequestMapping(value="/login", method= RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestBody UserDetails user,HttpServletRequest request,HttpSession session) {
		System.out.println("email "+user.getEmail()+" password "+user.getPassword());
		user=userservice.loginUser(user);
		session.setAttribute("user", user);

		if(user != null){
			String accessToken = GenerateToken.generateToken(user.getId());
			System.out.println("token "+accessToken);
			token.setGenerateToken(accessToken);
			String url = request.getRequestURL().toString();
			url = url.substring(0, url.lastIndexOf("/")) + "/" + "finalLogin" + "/" + accessToken;
			System.out.println("url : "+url);
			userservice.saveTokenInRedis(token);
			System.out.println("login successful!!!");
			return new ResponseEntity<String> (HttpStatus.OK);
		}else{
			System.out.println("login unsuccessful!!!");
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	@RequestMapping("/finalLogin/{token}")
	public ResponseEntity<String> checkValidUser(@PathVariable("token") String generateToken) {

		Token token = userservice.getToken(generateToken);
		if (token == null) {

			return new ResponseEntity<String>("token is incorrect", HttpStatus.BAD_REQUEST);
		}
		if (token.getGenerateToken().equals(generateToken)) {

			return new ResponseEntity<String>("successfull login", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Unsuccessfull login", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> logout(HttpSession session) {
		session.removeAttribute("user");
		session.invalidate();
		message.setMessage("Logout seccessful");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ErrorMessage forgotPassword(@RequestBody User user, HttpServletRequest request, HttpSession session) {

		String url = request.getRequestURL().toString();
		int lastIndex = url.lastIndexOf("/");
		String urlofForgotPassword = url.substring(0, lastIndex) + "#!/resetpassword";
		user = userService.emailValidate(user.getEmail());
		if (user == null) {
			message.setMessage("Please enter valid emailID");
			message.setStatus(500);
			return message;
		}
		try {
			String generateOTP = GenerateJWT.generate(user.getId());
			session.setAttribute("Token", generateOTP);
			mailService.sendEmail("om4java@gmail.com", user.getEmail(), "",
					urlofForgotPassword + "");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(400);
			return message;
		}
		message.setMessage("Forget Success");
		message.setStatus(200);
		return message;
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public ErrorMessage resetPassword(@RequestBody User user, HttpSession session) {
		
		String email = user.getEmail();
		String password = user.getPassword();
		System.out.println("Inside reset");
		
		int userId = VerifiedJWT.verify((String) session.getAttribute("Token"));
	
		if (userId == 0) {
			message.setMessage("Invalid OTP : ");
			message.setStatus(500);
			return message;
		}
		
		user = userService.emailValidate(email);
		if (user == null) {
			message.setMessage("User not found :");
			message.setStatus(500);
			return message;
		}
		
		user.setPassword(password);
		if (userService.updateUser(user)) {
			message.setMessage("Reset password is success :");
			message.setStatus(200);
			return message;
		} else {
			message.setMessage("Password could not be changed");
			message.setStatus(-200);
			return message;
		}
}*/
}
