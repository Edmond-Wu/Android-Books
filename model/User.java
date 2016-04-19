package com.example.edmond.android_books.model;

import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * @author Edmond Wu & Vincent Xie
 */

public class User implements Serializable {
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

	/**
	 * Serializes the user data
	 */
	public void serialize() {
		try {
	        FileOutputStream fileOut = new FileOutputStream("data/" + username + ".ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this);
	        out.close();
	        fileOut.close();
	    } catch(Exception e) {
	    	System.out.println("Invalid serialization.");
	    }
	}
}
