package com.bridgelabz.Sociallogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pooja todoApp
 *
 */
public class FacebookLogin {
	
	public static final String FB_APP_ID = "1490675564380779";
	public static final String FB_APP_SECRET = "00dba7c0cbe6515f7b8323c92fd4d119";
	public static final String REDIRECT_URI = "http://localhost:8080/todoApp/successFbLogin";
	private static final String USER_ACCESS_URL = "https://graph.facebook.com/v2.9/me?access_token=";
	private static final String BINDING = "&fields=id,name,email,first_name,last_name,picture";
	
	
	/**
	 * @return get facebook url and return it
	 */
	public static String generateFbUrl() {
		String url="";
		try {
			 url = "https://www.facebook.com/v2.10/dialog/oauth?" + "client_id=" + FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI) + "&state=todoappstate" + "&scope=public_profile,email";
			 System.out.println("hello");
			 /*url = "https://www.facebook.com/oauth/access_token?"
						+ "client_id=" + FB_APP_ID + "&redirect_uri="
						+ URLEncoder.encode(REDIRECT_URI, "UTF-8")
						+ "&client_secret=" + FB_APP_SECRET ;*/
			 //LOG.info("url for fb"+url);
		} catch (Exception e) {
			//LOG.catching(e);
		}
		return url;
		
	}
	/**
	 * @param code
	 * @return
	 * get facebook access token
	 */
	public static String getFbAccessToken(String code) {
		String urlParametersForFb = "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI)
		+ "&client_id=" + FB_APP_ID 
		+ "&client_secret=" + FB_APP_SECRET 
		+ "&code=" + code;
		try {
			URL url = new URL("https://graph.facebook.com/v2.10/oauth/access_token?"+urlParametersForFb);

			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(urlParametersForFb);
			writer.flush();
			
			String fbResponse = "";
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				fbResponse = fbResponse + line;
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			String fbAccessToken = objectMapper.readTree(fbResponse).get("access_token").asText();
			//LOG.info("fb access token:-"+fbAccessToken);
			
			return fbAccessToken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @param accessTokenForFb
	 * @return
	 * get the information about user by the token
	 */
	public static String getProfileInfoFromFb(String accessTokenForFb) {
		
		try {
			URL urlForFb = new URL(USER_ACCESS_URL+accessTokenForFb+BINDING);
			//LOG.info(urlForFb);
			System.out.println("inside get profile");
			URLConnection connection = urlForFb.openConnection();
			System.out.println("connect"+connection);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String fbProfileInfo="";
			String line;
			while((line=bufferedReader.readLine())!=null) {
				fbProfileInfo = line +fbProfileInfo;
			}
			System.out.println("profile info"+fbProfileInfo);
			return fbProfileInfo;
		} catch (Exception e) {
			//LOG.catching(e);
			//LOG.info("exception occured while getting url for fb:-");
		}
		return null;
	}
	

}