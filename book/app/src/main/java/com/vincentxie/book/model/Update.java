package com.vincentxie.book.model;

import java.util.*;
import java.io.*;

/**
 * Created by vincexie on 4/25/16.
 */
public class Update implements Serializable {

    private String update;
    private String description;
    private String title;
    private String author;
    private String cover;
    private Date time = Calendar.getInstance().getTime();
    private int id;

    /**
     * Default constructor for json serialization
     */
    public Update() {

    }

    public Update(String title, String author, String update, String description, String cover, int id){
        this.title = title;
        this.author = author;
        this.update = update;
        this.description = description;
        this.time = Calendar.getInstance().getTime();
        this.cover = cover;
        this.id = id;
    }

    public Date getTime(){
        return time;
    }

    /**
     * Get update
     * @return String update
     */
    public String getUpdate(){
        return update;
    }

    /**
     * Get description
     * @return String description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Get Book
     * @return Book
     */
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }

    /**
     * Get cover
     * @return cover
     */
    public String getCover(){
        return cover;
    }

    public int getId(){
        return id;
    }

}
