package com.bridgelabz.Util.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.Util.token.VerifyToken;

/**
 * @author Pooja todoApp
 *
 */
public class TokenIntercepter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * verifying the token , get the user from the token and save it in the request
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		System.out.println("intercepted : "+request.getHeader("token"));
		
		int userId = VerifyToken.verifyAccessToken(request.getHeader("token"));
		if (userId == 0) {
			response.setStatus(511);
			return false;
		}
		System.out.println("jhdewdjew wdjhwe wuyew--------> "+userId);
		request.setAttribute("userId", userId);
		return true;
	}
}
