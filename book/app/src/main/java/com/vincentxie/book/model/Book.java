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
	private List<Chapter> chapters;
	private List<Review> reviews;
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
	public List<Chapter> getChapters() {
		return chapters;
	}
	
	/**
	 * Gtes the book's reviews
	 * @return ArrayList of reviews
	 */
	public List<Review> getReviews() {
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

	/**
	 * Serializes the book data
	 */
	public void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("data/books/" + title + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch(Exception e) {
			System.out.println("Invalid serialization.");
		}
	}
}
