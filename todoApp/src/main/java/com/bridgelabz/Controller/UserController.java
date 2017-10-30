package com.bridgelabz.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;

/**
 * @author Pooja for todoApp User Controller
 *
 */

@RestController
public class UserController{
	
	@Autowired
	UserService userservice;
	@RequestMapping(value="/registrationForm", method= RequestMethod.POST)
	public void registartionUser(@RequestBody UserDetails user) {
		userservice.createUser(user);
		
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
}
