package com.bridgelabz.Sociallogin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.User.Service.UserService;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.response.CustomResponse;
import com.bridgelabz.Util.response.Response;
import com.bridgelabz.Util.token.GenerateToken;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pooja todoApp
 *
 */
@RestController
public class GoogleProfileData {
	@Autowired
	 UserService userService;
	
	/*@Autowired
	ErrorMessage message;*/
	
	/**
	 * @param request
	 * @param response
	 * google login
	 */
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
	/**
	 * @param request
	 * @param response
	 * @param session
	 * @return get the token from request and save all the data about the user in the database
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/successGoogleLogin")
	public ResponseEntity<Response> successGoogleLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
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
				googleUser.setProfileImage(objectMapper.readTree(googleProfileInfo).get("picture").asText());
				googleUser.setActivated(true);
				userService.createUser(googleUser);
				
				response.sendRedirect("http://localhost:8080/todoApp/#!/dummy");
			}
			else {
				String myAccessToken = GenerateToken.generateToken(userByEmail.getId());
				session.setAttribute("myAccessToken", myAccessToken);
				response.sendRedirect("http://localhost:8080/todoApp/#!/dummy");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage(accessToken);
		return ResponseEntity.ok(customResponse);
	}
}
