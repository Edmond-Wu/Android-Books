package com.vincentxie.book.model;

import java.io.Serializable;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Chapter implements Serializable, Comparable<Chapter> {
	private String title;
	private String text;
	private Date created;

	public Chapter() {

	}

	/**
	 * Chapter constructor
	 * @param c chapter title
	 * @param t chapter text body
	 */
	public Chapter(String c, String t) {
		title = c;
		text = t;
		created = new Date();
	}
	
	/**
	 * Gets the chapter title
	 * @return chapter title string
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the chapter's text in ArrayList form
	 * @return text string
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the date created
	 * @return created date
     */
	public Date getCreated() {
		return created;
	}

	/**
	 * Compares chapters
	 * @param c another chapter
	 * @return alphabetical comparsion by title
     */
	public int compareTo(Chapter c) {
		return title.compareTo(c.getTitle());
	}
}
