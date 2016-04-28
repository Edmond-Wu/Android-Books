package com.vincentxie.book.model;

import java.io.Serializable;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Review implements Serializable {
	private int rating;
	private int id;
	private String text;

	public Review() {

	}

	/**
	 * Review constructor
	 * @param star number rating (1 - 5)
	 * @param t review text
	 */
	public Review(int star, String t) {
		rating = star;
		text = t;
		id = (int) (Math.random() * (100000 - 1)) + 1;
	}

	/**
	 * Gets the id number for the review
	 * @return
     */
	public int getId() {
		return id;
	}

	/**
	 * Gets the review's numerical rating
	 * @return review stars
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * Gets the review's text body
	 * @return review string
	 */
	public String getText() {
		return text;
	}
}
