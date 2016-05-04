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
	private List<Genre> genres;
	private List<Chapter> chapters;
	private String cover;
	private String synopsis;
	private int id;

	/**
	 * Default constructor
	 */
	public Book() {
		chapters = new ArrayList<Chapter>();
		genres = new ArrayList<Genre>();
	}

	/**
	 * Book constructor
	 * @param t title
	 * @param a author
	 * @param g genre
	 * @param synop
     * @param c cover
     */
	public Book(String t, String a, List<Genre> g, String synop, String c) {
		title = t;
		chapters = new ArrayList<Chapter>();
		author = a;
		genres = g;
		cover = c;
		synopsis = synop;
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
	 * Gets synopsis
	 * @return
     */
	public String getSynopsis(){
		return synopsis;
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
	public List<Genre> getGenres() {
		return genres;
	}

	/**
	 * Gets the book's cover
	 * @return cover file name
     */
	public String getCover() {
		return cover;
	}

	/**
	 * Returns the book's id number
	 * @return
     */
	public int getId() {
		return id;
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
			String file_name = title + ".ser";
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
			e.printStackTrace();
		}
	}

	/**
	 * Sets the new title of the book
	 * @param new_title
     */
	public void setTitle(String new_title) {
		title = new_title;
	}

	/**
	 * Sets the book's author
	 * @param new_author
     */
	public void setAuthor(String new_author) {
		author = new_author;
	}

	/**
	 * Sets the book's synopsis
	 * @param syn
     */
	public void setSynopsis(String syn) {
		synopsis = syn;
	}

	/**
	 * Sets the book's cover
	 * @param cov
     */
	public void setCover(String cov) {
		cover = cov;
	}

	/**
	 * Sets the book's database id
	 * @param new_id
     */
	public void setId(int new_id) {
		id = new_id;
	}
}
