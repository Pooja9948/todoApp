package com.bridgelabz.Sociallogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pooja todoApp
 *
 */
public class GoogleLogin {
	
	private static final String clientId="182373184589-rcffghumgo0i19eedil3re370jfvaqms.apps.googleusercontent.com";
	private static final String secreateKey="e8U3inOWsI7Sy4vLfGOygSnc";
	private static final String redirectUrl="http://localhost:8080/todoApp/successGoogleLogin";
	
	/**
	 * @return 
	 * generate url and return it
	 */
	public static String generateGoogleUrl() {
		String googleLoginUrl="";
		try {
			googleLoginUrl="https://accounts.google.com/o/oauth2/auth?client_id=" + clientId + "&redirect_uri=" + 
					URLEncoder.encode(redirectUrl, "UTF-8") + "&response_type=code" + "&scope=profile email"
					+ "&approval_prompt=force" + "&access_type=offline";
		} catch (Exception e) {
		}
		return googleLoginUrl;
	}
	
	/**
	 * @param code
	 * @return
	 * get the access token
	 */
	public static String getAccessToken(String code) {
		 String urlParameters = "code=" + code + 
			       "&client_id=" + clientId +
			       "&client_secret=" + secreateKey + 
			       "&redirect_uri=" + URLEncoder.encode(redirectUrl) +
			       "&grant_type=authorization_code";
		 try {
				URL url = new URL("https://accounts.google.com/o/oauth2/token");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(urlParameters);
				writer.flush();
				String googleResponse = "";
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = bufferedReader.readLine()) != null){
					googleResponse = googleResponse + line;
				}
				ObjectMapper objectMapper = new ObjectMapper();
				
				String googleAccessToken;
				try {
					googleAccessToken = objectMapper.readTree(googleResponse).get("access_token").asText();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				return googleAccessToken;
			} catch (IOException e) {
				e.printStackTrace();
			}
			 return null;
	}
	/**
	 * @param accessToken
	 * @return
	 * get the profile information by the access token
	 */
	public static String getProfileData(String accessToken) {
		try {
			URL urlforGoogleProfile = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+accessToken);
			URLConnection connection = urlforGoogleProfile.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String googleProfileInfo="";
			String line;
			while((line = bufferedReader.readLine())!= null) {
				googleProfileInfo = googleProfileInfo+line; 
			}
			System.out.println("google profile info : "+googleProfileInfo);
			return googleProfileInfo;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
