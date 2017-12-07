package com.bridgelabz.Note.Controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteUrl;

public class NoteUrlService {

	static String pattern = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	String var;
	
	public static Set<String> checkUrl(String description) throws Exception {

		System.out.println("Inside url checker");
		//String title = note.getTitle();
		//String description = note.getDescription();
		Pattern patt = Pattern.compile(pattern);
		Matcher matcher = patt.matcher(description);
		Set<String> noteUrlList = null;	
		while (matcher.find()) {
			System.out.println("URL MATCHERSSSSSSS....." + matcher.start() + " and ending in " + matcher.end());
			if (noteUrlList == null) {
				noteUrlList =  new HashSet<>();
			}
			noteUrlList.add(description.substring(matcher.start(), matcher.end()));
		}
		return noteUrlList;
	}
	
	public List<NoteUrl> getUrls(String description) throws Exception {
		
		Set<NoteUrl> noteUrl = null;

		// sanity check
		if (description == null) {
			return null;
		}

		String trimmedString = description.trim();

		if (trimmedString == "") {
			return null;
		}
		
		Set<String> linkSet = checkUrl(trimmedString);
		
		if (linkSet != null) {
			noteUrl = new HashSet<>();
			for (String link : linkSet) {

				//NoteUrl noteUrl = new NoteUrl();
				//noteUrl.setUrl(link);

				URI uri = new URI(link);

				String domain = uri.getHost();

				// call another method to generate and save link metadata from each link
				//UrlMetaData urlMetaData = linkScraper.getUrlMetaData(link);

				String title = null;
				String imageUrl = null;
				
				/*Document doc = Jsoup.connect(description.substring(matcher.start(), matcher.end())).get();
				Elements metaOgTitle = doc.select("meta[property=og:title]");
				if (metaOgTitle != null) {
					title = metaOgTitle.attr("content");
					if (title == null) {
						title = doc.title();
					}
				}
				
				noteUrl.setUrlTitle(title);
				noteUrl.setUrlImage(imageUrl);
				noteUrl.setDomainName(domain);

				noteLinks.add(noteUrl);*/
			}
			return (List<NoteUrl>) noteUrl;
		}
		/*String title1 = null;
		String image = null;
		int count = 0;
		
			
			NoteUrl noteUrl = new NoteUrl();
			noteUrl.setUrlTitle(description.substring(matcher.start(), matcher.end()));
			Document doc = Jsoup.connect(description.substring(matcher.start(), matcher.end())).get();
			Elements metaOgTitle = doc.select("meta[property=og:title]");
			if (metaOgTitle != null) {
				title1 = metaOgTitle.attr("content");
				if (title1 == null) {
					title1 = doc.title();
				}
			}
			// domain??
			noteUrl.setUrlTitle(title);
			System.out.println("TITLE FROM THE URL --->" + title);

			Elements metaOgImage = doc.select("meta[property=og:image]");
			if (metaOgImage != null) {
				image = metaOgImage.attr("content");
			}
			System.out.println("IMAGE FROM THE URL --->" + image);
			noteUrl.setUrlImage(image);

			noteUrlList.add(noteUrl);
			System.out.println("list of url add " + noteUrlList.add(noteUrl));
			count++;

			if (description == null) {
				return null;
			}

			String trimmedString = description.trim();

			if (trimmedString == "") {
				return null;
			}
		

		
		 * if(count > 0) return true;
		 
		return noteUrlList;*/
		return null;
	}
}
