package com.vincentxie.book.model;

import java.io.*;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Book implements Serializable {
	private String title;
	private String author;
	private String genre;
	private ArrayList<Chapter> chapters;
	private ArrayList<Review> reviews;
	private File cover;
	
	/**
	 * Book constructor
	 * @param t title of book
	 */
	public Book(String t, String a, String g, File c) {
		title = t;
		chapters = new ArrayList<Chapter>();
		reviews = new ArrayList<Review>();
		author = a;
		genre = g;
		cover = c;
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
	 * Returns the book's author
	 * @return author
     */
	public String getAuthor() {
		return author;
	}

	/**
	 * Return's the book's genre
	 * @return genre
     */
	public String getGenre() {
		return genre;
	}

	/**
	 * Gets the book's cover
	 * @return cover file
     */
	public File getCover() {
		return cover;
	}

	/**
	 * Gets string representation of book.
	 * @return
     */
	public String toString(){
		return title + " - " + author;
	}
}
