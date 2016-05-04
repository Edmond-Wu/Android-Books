package com.vincentxie.book.model;

import java.util.*;
import java.io.*;

/**
 * Created by vincexie on 4/25/16.
 */
public class Update implements Serializable {

    private String update;
    private String desc;
    private String title;
    private String author;
    private String cover;
    private Date time;

    /**
     * Default constructor for json serialization
     */
    public Update() {

    }

    public Update(String title, String author, String update, String description, String cover){
        this.title = title;
        this.author = author;
        this.update = update;
        this.desc = description;
        this.time = Calendar.getInstance().getTime();
        this.cover = cover;
    }

    public Update(String title, String author, String update, String description, String cover, Date time){
        this(title, author, update, description, cover);
        this.time = time;
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
        return desc;
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
     * @return
     */
    public String getCover(){
        return cover;
    }

}
