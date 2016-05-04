package com.vincentxie.book.model;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class User implements Serializable {
	private String username;
	private String password;
<<<<<<< HEAD
	private HashMap<String, Boolean> subscribed_books;
	private HashMap<String, List<Bookmark>> bookmarks;
	private HashMap<String, Float> ratings;
=======
	private HashSet<Book> subscribed_books;
	private HashMap<Book, List<Bookmark>> bookmarks;
	private HashMap<Book, Float> ratings;
>>>>>>> b1e1edf859ead4bfc2dda0c73a8351319b1da316
	private List<Update> updates;

	/**
	 * Default constructor for json serialization purposes
	 */
	public User() {

	}

	/**
	 * User constructor
	 * @param u Username string
	 * @param p Password string
	 */
	public User(String u, String p) {
		username = u.toLowerCase();
		password = p;
<<<<<<< HEAD
		subscribed_books = new HashMap<String, Boolean>();
		bookmarks = new HashMap<String, List<Bookmark>>();
		ratings = new HashMap<String, Float>();
=======
		subscribed_books = new HashSet<Book>();
		bookmarks = new HashMap<Book, List<Bookmark>>();
		ratings = new HashMap<Book, Float>();
>>>>>>> b1e1edf859ead4bfc2dda0c73a8351319b1da316
		updates = new ArrayList<Update>();
	}

	/**
	 * Gets the username
	 * @return username string
	 */
	public String getUser() {
		return username;
	}

	/**
	 * Gets the password
	 * @return password string
	 */
	public String getPass() {
		return password;
	}

	/**
	 * Gets the list of books a user is subscribed to
	 * @return HashMap of subscribed books
	 */
<<<<<<< HEAD
	public HashMap<String, Boolean> getSubscriptions() {
=======
	public HashSet<Book> getSubscriptions() {
>>>>>>> b1e1edf859ead4bfc2dda0c73a8351319b1da316
		return subscribed_books;
	}

	/**
	 * Get list of updates
	 * @return updates
	 */
	public List<Update> getUpdates(){
		return updates;
	}

	/**
	 * Add an update.
	 * @param update
	 */
	public void addUpdate(Update update){
		updates.add(0, update);
	}

	public void setUsername(String name) {
		username = name;
	}

	/**
	 * Serializes the user data
	 */
	public void serialize(Context context) {
		FileOutputStream fileOut;
		try {
<<<<<<< HEAD
			String file_name = "user" + ".ser";
=======
			String file_name = "user.ser";
>>>>>>> b1e1edf859ead4bfc2dda0c73a8351319b1da316
			fileOut = context.openFileOutput(file_name, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch(Exception e) {
			System.out.println("Invalid user serialization");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the user's reviews list
	 * @return reviews
	 */
	public HashMap<String, Float> getRatings() {
		return ratings;
	}

	/**
	 * Gets the user's bookmark list
	 * @return bookmark list
	 */
	public HashMap<String, List<Bookmark>> getBookmarks() {
		return bookmarks;
	}

	/**
	 * Json serialization
	 * @param context
	 */
	public void toJson(Context context) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(context.openFileOutput("user.json", Context.MODE_PRIVATE), this);
		} catch (Exception e) {
			System.out.println("Invalid json serialization.");
		}
	}
}
