package com.vincentxie.book.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vincentxie.book.model.Book;
import com.vincentxie.book.model.Chapter;
import com.vincentxie.book.model.Review;

import java.io.*;
import java.util.*;

/**
 * Created by EDMOUND on 4/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static Context mContext;

    private static final String DB_NAME = "books.db";

    //tables
    private final static String TABLE_BOOK = "books";
    private final static String TABLE_CHAPTER = "chapters";
    private final static String TABLE_REVIEW = "reviews";

    //common columns
    private final static String KEY_ID = "id";

    //book columns
    private final static String KEY_BOOK_TITLE = "book_title";
    private final static String KEY_AUTHOR = "author";
    private final static String KEY_SYNOPSIS = "synopsis";
    private final static String KEY_COVER = "cover";

    //chapter columns
    private final static String KEY_CHAPTER_TITLE = "chapter_title";
    private final static String KEY_DATE = "date";
    private final static String KEY_CHAPTER_TEXT = "chapter_text";

    //review columns
    private final static String KEY_RATING = "rating";
    private final static String KEY_REVIEW_TEXT = "review_text";

    //chapter & review columns
    private final static String KEY_BOOK_ID = "book_id";

    //Table creation strings
    private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_TITLE + " TEXT," + KEY_AUTHOR + " TEXT," + KEY_SYNOPSIS + " TEXT," + KEY_COVER + " TEXT" + ")";
    private static final String CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE_CHAPTER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_ID + " INTEGER," + KEY_CHAPTER_TITLE + " TEXT," + KEY_DATE + " DATETIME," + KEY_CHAPTER_TEXT + " TEXT" + ")";
    private static final String CREATE_TABLE_REVIEW = "CREATE TABLE " + TABLE_REVIEW + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_ID + " INTEGER," + KEY_RATING + " INTEGER," + KEY_REVIEW_TEXT + " TEXT" + ")";

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public long createBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_SYNOPSIS, book.getSynopsis());
        values.put(KEY_COVER, book.getCover());

        long book_id = db.insert(TABLE_BOOK, null, values);



        return book_id;
    }

    public Book getBook(long book_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String select_query = "SELECT  * FROM " + TABLE_BOOK + " WHERE " + KEY_ID + " = " + book_id;
        Log.e(LOG, select_query);

        Cursor c = db.rawQuery(select_query, null);

        if (c != null) {
            c.moveToFirst();
        }

        Book book = new Book();
        book.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        book.setTitle(c.getString(c.getColumnIndex(KEY_BOOK_TITLE)));
        book.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
        book.setSynopsis(c.getString(c.getColumnIndex(KEY_SYNOPSIS)));
        book.setCover((c.getString(c.getColumnIndex(KEY_COVER))));

        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        String select_query = "SELECT  * FROM " + TABLE_BOOK;

        Log.e(LOG, select_query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(select_query, null);

        if (c.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                book.setTitle(c.getString(c.getColumnIndex(KEY_BOOK_TITLE)));
                book.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
                book.setSynopsis(c.getString(c.getColumnIndex(KEY_SYNOPSIS)));
                book.setCover((c.getString(c.getColumnIndex(KEY_COVER))));

                books.add(book);
            } while (c.moveToNext());
        }
        return books;
    }

    public List<Book> getAllBooksByAuthor(String author_name) {
        List<Book> books = new ArrayList<Book>();
        String select_query = "SELECT  * FROM " + TABLE_BOOK + " WHERE "
                + KEY_AUTHOR + " = " + author_name;

        Log.e(LOG, select_query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(select_query, null);

        if (c.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                book.setTitle(c.getString(c.getColumnIndex(KEY_BOOK_TITLE)));
                book.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
                book.setSynopsis(c.getString(c.getColumnIndex(KEY_SYNOPSIS)));
                book.setCover((c.getString(c.getColumnIndex(KEY_COVER))));

                books.add(book);
            } while (c.moveToNext());
        }
        return books;
    }

    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BOOK_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_SYNOPSIS, book.getSynopsis());
        values.put(KEY_COVER, book.getCover());

        return db.update(TABLE_BOOK, values, KEY_ID + " = ?", new String[]{String.valueOf(book.getId())});
    }

    public void deleteBook(long book_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOK, KEY_ID + " = ?", new String[]{String.valueOf(book_id)});
    }

    public long createReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RATING, review.getRating());
        values.put(KEY_REVIEW_TEXT, review.getText());

        long review_id = db.insert(TABLE_REVIEW, null, values);
        return review_id;
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<Review>();
        String select_query = "SELECT  * FROM " + TABLE_REVIEW;

        Log.e(LOG, select_query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(select_query, null);

        if (c.moveToFirst()) {
            do {
                Review review = new Review();
                review.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                review.setRating(c.getFloat(c.getColumnIndex(KEY_RATING)));
                review.setText(c.getString(c.getColumnIndex(KEY_REVIEW_TEXT)));

                reviews.add(review);
            } while (c.moveToNext());
        }
        return reviews;
    }

    public long createChapter(Chapter chapter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHAPTER_TITLE, chapter.getTitle());
        values.put(KEY_CHAPTER_TEXT, chapter.getTitle());

        long review_id = db.insert(TABLE_REVIEW, null, values);
        return review_id;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK);
        db.execSQL(CREATE_TABLE_CHAPTER);
        db.execSQL(CREATE_TABLE_REVIEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        onCreate(db);
    }
}
