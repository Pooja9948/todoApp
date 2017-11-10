package com.bridgelabz.SocialUtility;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.ErrorMessage;
import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;
import com.bridgelabz.token.GenerateToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleProfileData {
	@Autowired
	 UserService userService;
	
	@Autowired
	ErrorMessage message;
	
	@RequestMapping(value="/googleLogin")
	public void googleLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String googleUrl=GoogleLogin.generateGoogleUrl();
		try {
			response.sendRedirect(googleUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("testing i'm right");
	}
	@SuppressWarnings("unused")
	@RequestMapping(value="/successGoogleLogin")
	public ResponseEntity<ErrorMessage> successGoogleLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
		String code = (String)request.getParameter("code");
		String accessToken = GoogleLogin.getAccessToken(code);
		String googleProfileInfo = GoogleLogin.getProfileData(accessToken);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String email = objectMapper.readTree(googleProfileInfo).get("email").asText();
			UserDetails userByEmail = userService.emailValidation(email);
			if(userByEmail==null) {
				UserDetails googleUser = new UserDetails();
				googleUser.setEmail(objectMapper.readTree(googleProfileInfo).get("email").asText());
				googleUser.setFirstname(objectMapper.readTree(googleProfileInfo).get("given_name").asText());
				googleUser.setLastname(objectMapper.readTree(googleProfileInfo).get("family_name").asText());
				googleUser.setActivated(true);
				userService.createUser(googleUser);
				String myAccessToken = GenerateToken.generateToken(userByEmail.getId());
				System.out.println("checking access token"+myAccessToken);
				session.setAttribute("myAccessToken", myAccessToken);
				response.sendRedirect("http://localhost:8080/todo/#!/homePage");
			}
			else {
				String myAccessToken = GenerateToken.generateToken(userByEmail.getId());
				session.setAttribute("myAccessToken", myAccessToken);
				response.sendRedirect("http://localhost:8080/todo/#!/dummyLogin");
			}
			
			String id = objectMapper.readTree(googleProfileInfo).get("id").asText();
			String verified_email = objectMapper.readTree(googleProfileInfo).get("verified_email").asText(); 
			String given_name = objectMapper.readTree(googleProfileInfo).get("given_name").asText();
			String family_name=objectMapper.readTree(googleProfileInfo).get("family_name").asText();
			String gender = objectMapper.readTree(googleProfileInfo).get("gender").asText();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		message.setMessage(accessToken);
		return ResponseEntity.ok(message);
	}
}
