package com.read.files;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Movie implements Comparable<Movie> {
	Integer year;
	String name;
	String ratingImdb;
	String ratingRt;
	int ratingScore = 0;
	static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

	public int getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore() {
		try {
			if (null != this.getRatingImdb() && !this.getRatingImdb().equals("")) {
				this.ratingScore = (int) ((double) engine.eval(this.getRatingImdb()) * 100 + 1);
			} else if (null != this.getRatingRt() && !this.getRatingRt().equals("")) {
				this.ratingScore = (int) ((double) engine.eval(this.getRatingRt()) * 100 + 1);
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public String getRatingImdb() {
		return ratingImdb;
	}

	public void setRatingImdb(String ratingImdb) {
		this.ratingImdb = ratingImdb;
	}

	public String getRatingRt() {
		return ratingRt;
	}

	public void setRatingRt(String ratingRt) {
		this.ratingRt = ratingRt;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.getYear() + "  RT:   " + this.getRatingRt() + "  IMDB:   " + this.getRatingImdb() + " "
				+ this.getName();
	}

	@Override
	public int compareTo(Movie o) {
		return compareWithRating(o);
		// return compareWithYear(o);
	}

	public int compareWithRating(Movie o) {
		if (this.getRatingScore() > o.getRatingScore())
			return 1;
		else if (this.getRatingScore() < o.getRatingScore())
			return -1;
		else
			return 0;

	}

	public int compareWithYear(Movie o) {
		return this.getYear().compareTo(o.getYear());
	}
}
