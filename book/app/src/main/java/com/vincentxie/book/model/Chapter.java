package com.vincentxie.book.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Chapter implements Serializable, Comparable<Chapter> {
	private int id;
	private int bookid;
	private String title;
	private String text;
	private Date created;
	private String datestring;

	public Chapter() {
		created = new Date();
	}

	/**
	 * Chapter constructor
	 * @param c chapter title
	 * @param t chapter text
	 * @param bkid id number of the book associated with it
     */
	public Chapter(String c, String t, int bkid) {
		title = c;
		text = t;
		created = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH::mm::ss");
		datestring = df.format(created);
		id = (int) (Math.random() * (1000000 - 1)) + 1;
		bookid = bkid;
	}

	/**
	 * Gets the chapter's id number
	 * @return
     */
	public int getId() {
		return id;
	}

	public int getBookid() {
		return bookid;
	}

	/**
	 * Gets the chapter title
	 * @return chapter title string
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the chapter's text in ArrayList form
	 * @return text string
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the date created
	 * @return created date
     */
	public Date getCreated() {
		return created;
	}

	/**
	 * Gets the date in string format
	 * @return
     */
	public String getDatestring() {
		return datestring;
	}

	/**
	 * Sets chapter id
	 * @param new_id
     */
	public void setId(int new_id) {
		id = new_id;
	}

	/**
	 * Sets the chapter title
	 * @param new_title
     */
	public void setTitle(String new_title) {
		title = new_title;
	}

	/**
	 * Sets the chapter text
	 * @param new_text
     */
	public void setText(String new_text) {
		text = new_text;
	}

	public void setDatestring(String date_string) {
		datestring = date_string;
	}

	public void setBookid(int new_book_id) {
		bookid = new_book_id;
	}

	public void setDateFromString() {
		String pattern = "MM/dd/yyyy HH::mm::ss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			created = formatter.parse(datestring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compares chapters
	 * @param c another chapter
	 * @return alphabetical comparsion by title
     */
	public int compareTo(Chapter c) {
		return title.compareTo(c.getTitle());
	}
}
