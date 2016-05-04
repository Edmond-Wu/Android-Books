package com.vincentxie.book.model;

import java.io.*;

/**
 * Created by EDMOUND on 4/21/2016.
 */
public class Bookmark implements Serializable {
    private int scroll;
    private int chapter;
    private String name;

    public Bookmark() {

    }

    public Bookmark(int scr, int chap, String name) {
        scroll = scr;
        chapter = chap;
        this.name = name;
    }

    public int getScroll() {
        return scroll;
    }

    public int getChapter() {
        return chapter;
    }

    public String getName(){
        return name;
    }
}
