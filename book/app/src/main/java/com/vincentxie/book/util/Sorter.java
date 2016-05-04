package com.vincentxie.book.util;
import com.vincentxie.book.controller.MainMenu;
import com.vincentxie.book.model.Book;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;

/**
 * Created by vincexie on 4/28/16.
 */
public class Sorter {

    private static HashMap<String, Float> ratings;

    private Sorter(){

    }

    /**
     * Sort list by author
     * @param books
     * @return list sorted by author
     */
    public static List<Book> sortByAuthor(List<Book> books){
        Collections.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book book, Book t1) {
                return book.getAuthor().compareTo(t1.getAuthor());
            }
        });
        return books;
    }

    /**
     * Sort list by author
     * @param books
     * @return list sorted by title
     */
    public static List<Book> sortByTitle(List<Book> books){
        Collections.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book book, Book t1) {
                return book.getTitle().compareTo(t1.getTitle());
            }
        });
        return books;
    }

    /**
     * Sort list by author
     * @param books
     * @return list sorted by rating
     */
    public static List<Book> sortByRating(HashMap<String, Float> myRatings, List<Book> books){
        ratings = myRatings;
        Collections.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book book, Book t1) {
                int rating1 = 0, rating2 = 0;
                if(ratings.get(book.getTitle() + book.getAuthor()) != null){
                    rating1 = ratings.get(book.getTitle() + book.getAuthor()).intValue();
                }
                if(ratings.get(t1.getTitle() + t1.getAuthor()) != null){
                    rating2 = ratings.get(t1.getTitle() + t1.getAuthor()).intValue();
                }
                return (int)(rating2 - rating1);
            }
        });
        return books;
    }
}
