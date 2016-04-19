package model;

/**
 * @author Edmond Wu & Vincent Xie
 */

import java.util.*;

public class Book {
	private String title;
	private ArrayList<Chapter> chapters;
	
	/**
	 * Book constructor
	 * @param t title of book
	 */
	public Book(String t) {
		title = t;
		chapters = new ArrayList<Chapter>();
	}
	
	/**
	 * Gets the book title
	 * @return title string
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the book's list of chapters
	 * @return ArrayList of chapters
	 */
	public ArrayList<Chapter> getChapters() {
		return chapters;
	}
}
