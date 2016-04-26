package com.vincentxie.book.model;

import java.io.Serializable;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Review implements Serializable {
	private int rating;
	private String review;

	public Review() {

	}

	/**
	 * Review constructor
	 * @param star number rating (1 - 5)
	 * @param r review text
	 */
	public Review(int star, String r) {
		rating = star;
		review = r;
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
	public String getReview() {
		return review;
	}
}
