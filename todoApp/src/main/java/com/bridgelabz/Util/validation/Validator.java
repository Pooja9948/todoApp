package com.bridgelabz.Util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.User.Service.UserService;
import com.bridgelabz.User.model.UserDetails;

public class Validator {
	@Autowired
	UserService userService;
	
	public static final Pattern EMAILID = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",Pattern.CASE_INSENSITIVE);
	public static final Pattern NAME = Pattern.compile("^[a-zA-Z]{2,}$");
	public static final Pattern MOBILE = Pattern.compile("[0-9]{10}");
	public static final Pattern PASSWORD = Pattern.compile("^[a-zA-Z0-9]{8,}$");
	
	public String validateSaveUser(UserDetails userDetails) {
		
		String error = null;
		
		StringBuffer retValue = new StringBuffer();
		
		if (!validateRegEx(userDetails.getFirstname(), NAME)) {
			
			retValue.append("Your first name is short...");
		} 
		if (!validateRegEx(userDetails.getLastname(), NAME)) {
			
			retValue.append("your last name is short...");
		} 
		if (!validateRegEx(String.valueOf(userDetails.getMobileno()), MOBILE)) {
			
			retValue.append("Contact number must be 10 digits");
		} 
		if (!validateRegEx(userDetails.getPassword(), PASSWORD)) {
			
			retValue.append("Your password is short...");
		}
		if (!validateRegEx(userDetails.getConfirmpassword(), PASSWORD) && !userDetails.getConfirmpassword().equals(userDetails.getPassword())) {
			
			
			retValue.append("Password is not matches !!!");
			
		}
		if (!validateRegEx(userDetails.getEmail(), EMAILID)) {
			
			
			retValue.append("Please enter a valid email address");
		}else{
			UserDetails user = userService.emailValidation(userDetails.getEmail());
			if (user!=null) {
				
				retValue.append("Email already exists");
			} 
		}
		
		if(retValue.equals("") == false) {
		
			error = retValue.toString();
		
		}
		
		System.out.println("*****Error is "+error);
		
		return error;
	}
	
	boolean validateRegEx(String variable, Pattern match) {
		Matcher matcher = match.matcher(variable);
		return matcher.find();
	}
}
