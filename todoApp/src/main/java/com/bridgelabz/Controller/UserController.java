package com.bridgelabz.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Mail.SendMail;
import com.bridgelabz.model.Token;
import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;
import com.bridgelabz.token.GenerateToken;
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
	
	@RequestMapping(value="/registrationForm", method= RequestMethod.POST)
	public ResponseEntity<String> registrationUser(@RequestBody UserDetails user) {
		String isValidate = validator.validateSaveUser(user);
		System.out.println("check valid"+isValidate);
		if (isValidate.equals("")) {
			System.out.println("is validte "+isValidate);
			userservice.createUser(user);
			sendmail.sendMail("om4java@gmail.com", user.getEmail(), "welcome", "Registration successful");
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.CONFLICT);
		
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
	
}
