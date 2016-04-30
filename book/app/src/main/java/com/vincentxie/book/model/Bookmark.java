package com.vincentxie.book.model;

import java.io.*;

/**
 * Created by EDMOUND on 4/21/2016.
 */
public class Bookmark implements Serializable {
    private int scroll;
    private Chapter chapter;
    private String name;

    public Bookmark() {

    }

    public Bookmark(int scr, Chapter chap, String name) {
        scroll = scr;
        chapter = chap;
        this.name = name;
    }

    public int getScroll() {
        return scroll;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setScroll(int new_scroll) {
        scroll = new_scroll;
    }

    public void setChapter(Chapter new_chapter) {
        chapter = new_chapter;
    }

    public String getName(){
        return name;
    }
}
