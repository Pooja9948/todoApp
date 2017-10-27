package com.bridgelabz.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.bridgelabz.DAO.BankDAO;
import com.bridgelabz.pojo.UserDetails;

//@WebServlet("/Registration")
/**
 * @author Pooja for registration form page
 *
 */
public class Registration extends HttpServlet{
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param request
	 * @param response
	 * In this servlet, its saving all the information about user in the database and redirect to the loginpage
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html");
		
	    try {
	    	System.out.println("inside servlet------->");
	    	String name= request.getParameter("name");
	        String email= request.getParameter("email");
		    String password= request.getParameter("password");
		    String mobileno= request.getParameter("mobileno");
	        UserDetails userdetail=new UserDetails();	
	        userdetail.setName(name);
	        userdetail.setEmail(email);
	        userdetail.setPassword(password);
	        userdetail.setMobileno(mobileno);
	        BankDAO.registration(userdetail);
	        
	        System.out.println("outside servlet------->");
	        
	    }catch(Exception se) {
	         se.printStackTrace();
	      } finally {
	         try {
	          
	         } catch(Exception se) {
	            se.printStackTrace();
	         }
	      }
	    RequestDispatcher dispatcher= request.getRequestDispatcher("login.jsp");
	    dispatcher.include(request, response);
	}
}
