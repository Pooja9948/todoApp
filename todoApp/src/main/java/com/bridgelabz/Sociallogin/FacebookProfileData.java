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
import com.bridgelabz.Util.UrlUtil;
import com.bridgelabz.Util.response.CustomResponse;
import com.bridgelabz.Util.response.Response;
import com.bridgelabz.Util.token.GenerateToken;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pooja todoApp
 *
 */
@RestController
public class FacebookProfileData {
	//private Logger LOG = (Logger) LogManager.getLogger(FBLogin.class);

	@Autowired
	UserService userService;


	/**
	 * @param request
	 * @param response
	 * facebook login
	 */
	@RequestMapping(value="/fbLogin" ,method = RequestMethod.GET)
	public void fbLogin(HttpServletRequest request,HttpServletResponse response) {
		String fbUrl = FacebookLogin.generateFbUrl();
		try {
			response.sendRedirect(fbUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * get the token from he request
	 */
	@RequestMapping(value="/successFbLogin", method = RequestMethod.GET)
	public ResponseEntity<Response> getFbAccessToken(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String codeForFb = request.getParameter("code");
		String accessTokenForFb = FacebookLogin.getFbAccessToken(codeForFb);
		String profileInfoFromFB = FacebookLogin.getProfileInfoFromFb(accessTokenForFb);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String email = mapper.readTree(profileInfoFromFB).get("email").asText();
			System.out.println("email is : "+email);
			UserDetails userByEmail = userService.emailValidation(email);
			
			System.out.println("email : "+userByEmail);
			if(userByEmail==null) {
				 userByEmail = new UserDetails();
				 userByEmail.setEmail(mapper.readTree(profileInfoFromFB).get("email").asText());
				 userByEmail.setFirstname(mapper.readTree(profileInfoFromFB).get("first_name").asText());
				 userByEmail.setLastname(mapper.readTree(profileInfoFromFB).get("last_name").asText());
				 userByEmail.setProfileImage(mapper.readTree(profileInfoFromFB).get("picture").asText());
				 userByEmail.setActivated(true);
				userService.createUser(userByEmail);
				response.sendRedirect(UrlUtil.getUrl(request, "/#!/dummy"));
				//response.sendRedirect("http://localhost:8080/todoApp/#!/dummy");
			}else {
				String myAccessToken = GenerateToken.generateToken(userByEmail.getId());
				session.setAttribute("myAccessToken", myAccessToken);
				response.sendRedirect(UrlUtil.getUrl(request, "/#!/dummy"));
				//response.sendRedirect("http://localhost:8080/todoApp/#!/dummy");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	

}
