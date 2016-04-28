package com.vincentxie.book.model;

import java.io.Serializable;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class Chapter implements Serializable, Comparable<Chapter> {
	private int id;
	private String title;
	private String text;
	private Date created;

	public Chapter() {
		created = new Date();
	}

	/**
	 * Chapter constructor
	 * @param c chapter title
	 * @param t chapter text body
	 */
	public Chapter(String c, String t) {
		title = c;
		text = t;
		created = new Date();
		id = (int) (Math.random() * (1000000 - 1)) + 1;
	}

	/**
	 * Gets the chapter's id number
	 * @return
     */
	public int getId() {
		return id;
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

	/**
	 * Compares chapters
	 * @param c another chapter
	 * @return alphabetical comparsion by title
     */
	public int compareTo(Chapter c) {
		return title.compareTo(c.getTitle());
	}
}
