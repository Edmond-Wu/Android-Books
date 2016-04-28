package com.vincentxie.book.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.vincentxie.book.model.Book;

import java.io.*;
import java.util.*;

/**
 * Created by EDMOUND on 4/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static Context mContext;
    private static String DB_PATH = "/data/data/vincentxie.book/databases/";

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
    private final static String KEY_BOOK_ID = "book_id";
    private final static String KEY_CHAPTER_TITLE = "chapter_title";
    private final static String KEY_DATE = "date";
    private final static String KEY_CHAPTER_TEXT = "chapter_text";

    //review columns
    private final static String KEY_RATING = "rating";
    private final static String KEY_REVIEW_TEXT = "review_text";

    //Table creation strings
    private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_TITLE + " TEXT," + KEY_AUTHOR + " TEXT," + KEY_SYNOPSIS + " TEXT" + ")";
    private static final String CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE_CHAPTER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_ID + " INTEGER," + KEY_CHAPTER_TITLE + " TEXT," + KEY_DATE + " DATETIME," + KEY_CHAPTER_TEXT + " TEXT" + ")";
    private static final String CREATE_TABLE_REVIEW = "CREATE TABLE " + TABLE_REVIEW + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RATING + " INTEGER," + KEY_REVIEW_TEXT + " TEXT" + ")";

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public long createBook(Book book) {
        return 0;
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
