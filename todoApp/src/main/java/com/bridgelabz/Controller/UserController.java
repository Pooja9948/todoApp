package com.bridgelabz.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;
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
	
	@RequestMapping(value="/registrationForm", method= RequestMethod.POST)
	public ResponseEntity<String> registartionUser(@RequestBody UserDetails user) {
		String isValidate = validator.validateSaveUser(user);
		if (isValidate.equals("Success")) {
			userservice.createUser(user);
			return new ResponseEntity<String>(isValidate , HttpStatus.OK);
		}
		return new ResponseEntity<String>(isValidate,HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public void loginUser(@RequestBody UserDetails user,HttpSession session) {
		System.out.println("email "+user.getEmail()+" password "+user.getPassword());
		user=userservice.loginUser(user);
		session.setAttribute("user", user);
		if(user != null){
			System.out.println("login successful!!!");
		}else
			System.out.println("login unsuccessful!!!");
	}
	
	
	/**
	 * 
	 * @RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		String isValidator = validator.validateSaveUser(user);
		if (isValidator.equals("Success")) {
			userService.saveUser(user); 
			mailService.sendMail(user.getEmail());
			return new ResponseEntity<String>(isValidator,HttpStatus.OK);
		}
		return new ResponseEntity<String>(isValidator,HttpStatus.CONFLICT);
	}
	
	 * 
	 * 
	 */
}
