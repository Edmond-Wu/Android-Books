package com.vincentxie.book.model;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	//private List<Review> reviews;
	private Review review;
	private File cover;

	/**
	 * Default constructor
	 */
	public Book() {

	}

	/**
	 * Book constructor
	 * @param t title of book
	 */
	public Book(String t, String a, String g, File c) {
		title = t;
		chapters = new ArrayList<Chapter>();
		//reviews = new ArrayList<Review>();
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
	/*
	public List<Review> getReviews() {
		return reviews;
	}
	*/

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
	 * Returns the book's solo review
	 * @return review
     */
	public Review getReview() {
		return review;
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
	public void serialize(Context context) {
		FileOutputStream fileOut;
		try {
			String file_name = title + ".bin";
			fileOut = context.openFileOutput(file_name, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch(Exception e) {
			System.out.println("Invalid book serialization");
			e.printStackTrace();
		}
	}

	/**
	 * Json serialization
	 * @param context
     */
	public void toJson(Context context) {
		ObjectMapper mapper = new ObjectMapper();
		String file_name = "book-" + title + ".json";
		try {
			mapper.writeValue(context.openFileOutput(file_name, Context.MODE_PRIVATE), this);
		} catch (Exception e) {
			System.out.println("Invalid json serialization.");
		}
	}

	/**
	 * Sets the book's review
	 * @param rev a review
     */
	public void setReview(Review rev) {
		review = rev;
	}
}
