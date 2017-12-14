package com.bridgelabz.Util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {

	public static String getUrl(HttpServletRequest request, String suffix) throws MalformedURLException {
		URL url = new URL(request.getRequestURL().toString());
		String redirectUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + request.getContextPath()
				+ suffix;
		return redirectUrl;
	}

	public static String addAnchor(String link) {
		return "<html>\n" + "<body>\n" + "<a href='" + link + "'>Click here to activate your account!!!</a>\n"
				+ "</body>\n" + "</html>";
	}
}
