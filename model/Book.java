package model;

import java.io.Serializable;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Book implements Serializable {
	private String title;
	private HashMap<String, User> subscribers;
	private ArrayList<Chapter> chapters;
	private ArrayList<Review> reviews;
	
	/**
	 * Book constructor
	 * @param t title of book
	 */
	public Book(String t) {
		title = t;
		chapters = new ArrayList<Chapter>();
		reviews = new ArrayList<Review>();
		subscribers = new HashMap<String, User>();
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
	
	/**
	 * Gtes the book's reviews
	 * @return ArrayList of reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	
	/**
	 * Gets a map of the book's subscribers
	 * @return HashMap of subscribers
	 */
	public HashMap<String, User> getSubscribers() {
		return subscribers;
	}
}
