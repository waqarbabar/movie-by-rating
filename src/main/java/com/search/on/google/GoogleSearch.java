package com.search.on.google;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GoogleSearch {

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	static final Pattern MY_PATTERN = Pattern.compile("\\d+.?\\d\\/\\d+");

	public static String searchByStringOnGoogle(String searchTerm, String prefix, int num) throws IOException {
		String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchTerm + "&num=" + num;
		// without proper User-Agent, we will get 403 error
		Document doc = Jsoup.connect(searchURL).timeout(1000 * 100000).userAgent("Mozilla/5.0").get();

		// below will print HTML data, save it to a file and open in browser to compare

		String res = doc.html();
		if (res.contains(prefix)) {
			res = res.substring(res.indexOf(prefix) + prefix.length(), res.indexOf(prefix) + 100);
			Matcher m = MY_PATTERN.matcher(res);
			res = m.find() ? m.group(0) : "";
			// res = res.contains("\\d+.?\\d\\/\\d+");
		} else
			res = "";
		return res;
	}
}
