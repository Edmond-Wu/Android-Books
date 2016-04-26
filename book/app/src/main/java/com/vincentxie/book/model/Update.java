package com.vincentxie.book.model;

/**
 * Created by vincexie on 4/25/16.
 */
public class Update {

    private String update;
    private String desc;
    private Book book;

    public Update(Book book, String update, String description){
        this.book = book;
        this.update = update;
        this.desc = description;
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
