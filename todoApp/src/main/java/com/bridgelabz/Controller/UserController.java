package com.bridgelabz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;

//@WebServlet("/Registration")
/**
 * @author Pooja for registration form page
 *
 */

@RestController
public class UserController{
	@Autowired
	UserService userservice;
	@RequestMapping(value="/registrationForm", method= RequestMethod.POST)
	public void registartionUser(@ModelAttribute("registrationForm") UserDetails user) {
		userservice.createUser(user);
		
	}
}
