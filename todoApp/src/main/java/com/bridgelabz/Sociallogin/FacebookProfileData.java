package com.bridgelabz.Sociallogin;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.User.Service.UserService;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.response.CustomResponse;
import com.bridgelabz.Util.response.Response;
import com.bridgelabz.Util.token.GenerateToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class FacebookProfileData {
	//private Logger LOG = (Logger) LogManager.getLogger(FBLogin.class);

	@Autowired
	UserService userService;

	/*@Autowired
	ErrorMessage message;*/

	@RequestMapping(value="/fbLogin" ,method = RequestMethod.GET)
	public void fbLogin(HttpServletRequest request,HttpServletResponse response) {
		String fbUrl = FacebookLogin.generateFbUrl();
		try {
			//LOG.info("FB URL: " + fbUrl);
			response.sendRedirect(fbUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/successFbLogin", method = RequestMethod.GET)
	public ResponseEntity<Response> getFbAccessToken(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		//LOG.info("After success");
		String codeForFb = request.getParameter("code");
		//LOG.info("codeForFb:-"+codeForFb);
		String accessTokenForFb = FacebookLogin.getFbAccessToken(codeForFb);
		//LOG.info("accessTokenForFb:-"+accessTokenForFb);
		String profileInfoFromFB = FacebookLogin.getProfileInfoFromFb(accessTokenForFb);
		//LOG.info(profileInfoFromFB);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String email = mapper.readTree(profileInfoFromFB).get("email").asText();
			System.out.println("email is : "+email);
			UserDetails userByEmail = userService.emailValidation(email);
			
			System.out.println("email : "+userByEmail);
			//LOG.info("userByEmail:-"+userByEmail);
			if(userByEmail==null) {
				 userByEmail = new UserDetails();
				 userByEmail.setEmail(mapper.readTree(profileInfoFromFB).get("email").asText());
				 userByEmail.setFirstname(mapper.readTree(profileInfoFromFB).get("first_name").asText());
				 userByEmail.setLastname(mapper.readTree(profileInfoFromFB).get("last_name").asText());
				 userByEmail.setActivated(true);
				userService.createUser(userByEmail);

				response.sendRedirect("http://localhost:8080/todoApp/#!/homePage");
			}else {
				String myAccessToken = GenerateToken.generateToken(userByEmail.getId());
				//LOG.info("token geneted by jwt"+myAccessToken);
				session.setAttribute("myAccessToken", myAccessToken);
				response.sendRedirect("http://localhost:8080/todoApp/#!/dummyFbLogin");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//LOG.info("exception occured during registering user from fb:");
			//LOG.catching(e);
			e.printStackTrace();
		}
		return null;

	}
	@RequestMapping(value="/tokenAftergFbLogin")
	public ResponseEntity<Response> getAccessTokenByglogin(HttpSession session){
		String acessToken = (String) session.getAttribute("myAccessToken");
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage(acessToken);
		return ResponseEntity.ok(customResponse);

	}

}
