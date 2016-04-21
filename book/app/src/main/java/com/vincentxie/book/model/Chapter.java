package com.vincentxie.book.model;

import java.io.Serializable;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Chapter implements Serializable, Comparable<Chapter> {
	private String chapter_name;
	private String text;
	
	/**
	 * Chapter constructor
	 * @param c chapter title
	 * @param t chapter text body
	 */
	public Chapter(String c, String t) {
		chapter_name = c;
		text = t;
	}
	
	/**
	 * Gets the chapter title
	 * @return chapter title string
	 */
	public String getChapterTitle() {
		return chapter_name;
	}
	
	/**
	 * Gets the chapter's text in ArrayList form
	 * @return text string
	 */
	public String getText() {
		return text;
	}

	/**
	 * Compares chapters
	 * @param c another chapter
	 * @return alphabetical comparsion by title
     */
	public int compareTo(Chapter c) {
		return chapter_name.compareTo(c.getChapterTitle());
	}
}
