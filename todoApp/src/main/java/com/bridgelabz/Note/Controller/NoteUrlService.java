package com.bridgelabz.Note.Controller;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Note.DAO.NoteDAO;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteUrl;

/**
 * @author Pooja todoApp
 *
 */
public class NoteUrlService {
	@Autowired
	NoteDAO noteDao;
	static String pattern = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	String var;

	/**
	 * @param description
	 * @return
	 * @throws Exception
	 * Checking the url from description
	 */
	public static Set<String> checkUrl(String description) throws Exception {

		System.out.println("Inside url checker");
		// String title = note.getTitle();
		// String description = note.getDescription();
		Pattern patt = Pattern.compile(pattern);
		Matcher matcher = patt.matcher(description);
		Set<String> noteUrlList = null;
		while (matcher.find()) {
			System.out.println("URL MATCHERSSSSSSS....." + matcher.start() + " and ending in " + matcher.end());
			if (noteUrlList == null) {
				noteUrlList = new HashSet<>();
			}
			noteUrlList.add(description.substring(matcher.start(), matcher.end()));
		}
		return noteUrlList;
	}

	/**
	 * @param description
	 * @return
	 * @throws Exception
	 * to get urls set in NoteUrls
	 */
	public static Set<NoteUrl> getUrls(String description) throws Exception {

		Set<NoteUrl> noteUrl = null;

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

				NoteUrl noteUrl1 = new NoteUrl();
				noteUrl1.setUrl(link);

				URI uri = new URI(link);

				String domain = uri.getHost();

				String title = null;
				String imageUrl = null;
				Pattern patt = Pattern.compile(pattern);
				Matcher matcher = patt.matcher(description);
				while (matcher.find()) {
					Document doc = Jsoup.connect(trimmedString.substring(matcher.start(), matcher.end())).get();

					Elements metaOgTitle = doc.select("meta[property=og:title]");
					if (metaOgTitle != null) {
						title = metaOgTitle.attr("content");
						if (title == null) {
							title = doc.title();
						}
					}
					Elements metaOgImage = doc.select("meta[property=og:image]");
					if (metaOgImage != null) {
						imageUrl = metaOgImage.attr("content");
					}
				}
				noteUrl1.setUrlTitle(title);
				noteUrl1.setUrlImage(imageUrl);
				noteUrl1.setDomainName(domain);

				noteUrl.add(noteUrl1);
			}
			return noteUrl;
		}
		return null;
	}

	/**
	 * @param noteDetails
	 * @return
	 * @throws Exception
	 * creating urls, saving it and return object of NoteUrls
	 */
	public Set<NoteUrl> createNoteUrls(NoteDetails noteDetails) throws Exception {

		Set<NoteUrl> noteUrls = NoteUrlService.getUrls(noteDetails.getDescription());

		if (noteUrls != null) {
			noteDao.saveNoteUrls(noteUrls, noteDetails);
			return noteUrls;
		}
		return noteUrls;

	}
}
