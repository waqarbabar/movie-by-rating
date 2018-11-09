package com.read.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.search.on.google.GoogleSearch;

public class ReadFolderName {
	static int count = 0;
	static final String output_file = "MovieNamesWithRatings.txt";
	static final Pattern pattern = Pattern.compile("\\d{4}");
	static FileWriter fw;
	static List<Movie> movieList = new ArrayList<>();

	public static void main(String[] args) {

		File folder = new File("\\\\10.10.10.100\\Hollywood");

		try {
			readAllFileNames(folder);
			// Collections.sort(yearNameList, Collections.reverseOrder());

			for (Movie m : movieList) {
				m.setRatingRt(GoogleSearch.searchByStringOnGoogle(m.getName() + " rotten tomato rating",
						"<b>Rating</b>:", 1));
				m.setRatingImdb(GoogleSearch.searchByStringOnGoogle(m.getName() + " imdb rating", "Rating:", 1));
				if ((null != m.getRatingRt() && !m.getRatingRt().equals("")) ||(null != m.getRatingImdb() && !m.getRatingImdb().equals(""))) {
					m.setRatingScore();
				}

				System.out.println(m);
			}
			Collections.sort(movieList);
			Collections.reverse(movieList);
			FileWriter fw = new FileWriter(output_file);
			for (Movie m : movieList) {
				System.out.println(m.getRatingScore());
				if (null != m.getRatingImdb() && !m.getRatingImdb().equals("")) {
					fw.write("IMDB: " + m.getRatingImdb() + " " + m.getName() + " " + m.getYear() + " \n");
				}
				if (null != m.getRatingRt() && !m.getRatingRt().equals("")) {
					fw.write("RT:   " + m.getRatingRt() + " " + m.getName() + " " + m.getYear() + " \n");
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(count);

	}

	public static void readAllFileNames(File folder) throws IOException {
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (findFileFormat(listOfFiles[i].getName()).matches("mkv|avi|mp4|m4v")) {

					System.out.println(listOfFiles[i].getName());
					String[] onlyName = listOfFiles[i].getName().split("\\d{4}");
					Matcher m = pattern.matcher(listOfFiles[i].getName());
					String year = m.find() ? m.group(0) : "";
					// appends the string to the file
					String str = onlyName[0];
					str = (str != null && str.length() > 0
							&& (str.charAt(str.length() - 1) == '.' || str.charAt(str.length() - 1) == '('))
									? str.substring(0, str.length() - 1).trim()
									: str.trim();
					str = str.replace(".", " ");
					if (str.toUpperCase().contains("SAMPLE"))
						continue;
					count++;
					System.out.println(str);
					Movie movie = new Movie();
					if (isInteger(year)) {
						movie.setName(str);
						movie.setYear(Integer.parseInt(year));
					} else {
						movie.setName(str);
						movie.setYear(9999);
					}
					movieList.add(movie);
				}
			} else if (listOfFiles[i].isDirectory()) {
				readAllFileNames(listOfFiles[i]);
			}
		}
	}

	public static void writeNamesAndRatings() {

	}

	public static String findFileFormat(String fileName) {
		String[] vals = fileName.split("\\.");
		return vals[vals.length - 1];
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
