package com.vincentxie.book.model;

import java.io.*;

/**
 * Created by EDMOUND on 4/29/2016.
 */
public class Genre implements Serializable {
    private int id;
    private String genre;

    public Genre() {

    }

    public Genre(String g) {
        genre = g;
    }

    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setId(int new_id) {
        id = new_id;
    }

    public void setGenre(String new_genre) {
        genre = new_genre;
    }
}
