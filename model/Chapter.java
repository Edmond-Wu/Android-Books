package model;

import java.io.Serializable;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Chapter implements Serializable {
	private String chapter_name;
	private ArrayList<String> text;
	
	/**
	 * Chapter constructor
	 * @param c chapter title
	 */
	public Chapter(String c) {
		chapter_name = c;
		text = new ArrayList<String>();
	}
	
	/**
	 * Gets the chapter title
	 * @return chapter title string
	 */
	public String chapterTitle() {
		return chapter_name;
	}
	
	/**
	 * Gets the chapter's text in ArrayList form
	 * @return ArrayList of text
	 */
	public ArrayList<String> getText() {
		return text;
	}
}
