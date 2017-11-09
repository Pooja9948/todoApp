package com.bridgelabz.SocialUtility;
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

import com.bridgelabz.model.ErrorMessage;
import com.bridgelabz.model.UserDetails;
import com.bridgelabz.service.UserService;
import com.bridgelabz.token.GenerateToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class FacebookProfileData {
	//private Logger LOG = (Logger) LogManager.getLogger(FBLogin.class);

	@Autowired
	UserService userService;

	@Autowired
	ErrorMessage message;

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
	public ResponseEntity<ErrorMessage> getFbAccessToken(HttpServletRequest request,HttpServletResponse response,HttpSession session){
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
			//LOG.info(email);
			UserDetails userByEmail = userService.getUserByEmail(email);
			//LOG.info("userByEmail:-"+userByEmail);
			if(userByEmail==null) {
				UserDetails userForFb = new UserDetails();
				userForFb.setEmail(mapper.readTree(profileInfoFromFB).get("email").asText());
				userForFb.setFirstname(mapper.readTree(profileInfoFromFB).get("first_name").asText());
				userForFb.setLastname(mapper.readTree(profileInfoFromFB).get("last_name").asText());
				userForFb.setActivated(true);
				userService.createUser(userForFb);

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
	public ResponseEntity<ErrorMessage> getAccessTokenByglogin(HttpSession session){
		String acessToken = (String) session.getAttribute("myAccessToken");
		message.setMessage(acessToken);
		return ResponseEntity.ok(message);

	}

}
