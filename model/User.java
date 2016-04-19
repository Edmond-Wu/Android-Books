package model;

/**
 * @author Edmond Wu & Vincent Xie
 */

import java.util.*;

public class User {
	private String username;
	private String password;
	private HashMap<String, Book> subscribed_books;
	
	/**
	 * User constructor
	 * @param u Username string
	 * @param p Password string
	 */
	public User(String u, String p) {
		username = u.toLowerCase();
		password = p;
		subscribed_books = new HashMap<String, Book>();
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
	public HashMap<String, Book> getSubscriptions() {
		return subscribed_books;
	}
}
