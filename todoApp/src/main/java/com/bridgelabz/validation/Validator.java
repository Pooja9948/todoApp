package com.bridgelabz.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;

public class Validator {
	@Autowired
	UserService userService;
	
	public static final Pattern EMAILID = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",Pattern.CASE_INSENSITIVE);
	public static final Pattern NAME = Pattern.compile("^[a-zA-Z]{2,}$");
	public static final Pattern MOBILE = Pattern.compile("[0-9]{10}");
	public static final Pattern PASSWORD = Pattern.compile("^[a-zA-Z0-9]{8,}$");
	
	public String validateSaveUser(UserDetails userDetails) {

		if (!validateRegEx(userDetails.getFirstname(), NAME)) {
			return "Your first name is short...";
		} 
		else if (!validateRegEx(userDetails.getLastname(), NAME)) {
			return "your last name is short...";
		} 
		else if (!validateRegEx(userDetails.getEmail(), EMAILID)) {
			return "Please enter a valid email address";
		} 
		else if (!validateRegEx(String.valueOf(userDetails.getMobileno()), MOBILE)) {
			return "Contact number must be 10 digits";
		} 
		else if (!validateRegEx(userDetails.getPassword(), PASSWORD)) {
			return "Your password too short!!";
		} 
		else {
			boolean res = userService.emailValidation(userDetails.getEmail());
			if (res) {
				return "Email already exists";
			} else {
				return "Success";
			}
		}
	}
	
	boolean validateRegEx(String variable, Pattern match) {
		Matcher matcher = match.matcher(variable);
		return matcher.find();
	}
}
