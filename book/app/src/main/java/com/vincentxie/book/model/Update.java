package com.vincentxie.book.model;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by vincexie on 4/25/16.
 */
public class Update {

    private String update;
    private String desc;
    private Book book;
    private Date time;

    public Update(Book book, String update, String description){
        this.book = book;
        this.update = update;
        this.desc = description;
        time = Calendar.getInstance().getTime();
    }

    public Update(Book book, String update, String description, Date time){
        this(book, update, description);
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
    public Book getBook(){
        return book;
    }

}
