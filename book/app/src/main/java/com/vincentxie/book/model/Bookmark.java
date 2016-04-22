package com.vincentxie.book.model;

import java.io.*;

/**
 * Created by EDMOUND on 4/21/2016.
 */
public class Bookmark implements Serializable {
    private double scroll;
    private Chapter chapter;

    public Bookmark(double scr, Chapter chap) {
        scroll = scr;
        chapter = chap;
    }

    public double getScroll() {
        return scroll;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setScroll(double new_scroll) {
        scroll = new_scroll;
    }

    public void setChapter(Chapter new_chapter) {
        chapter = new_chapter;
    }
}
