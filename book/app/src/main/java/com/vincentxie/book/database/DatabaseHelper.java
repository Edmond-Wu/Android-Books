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
    public final static String BK_TABLE = "Books";
    public final static String KEY_TITLE = "title";
    public final static String KEY_AUTHOR = "author";
    private static final int DB_VERSION = 10;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void createDatabase() throws IOException {
        boolean exists = checkDatabase();
        if (exists) {
            this.getWritableDatabase();
        }
        exists = checkDatabase();
        if (!exists) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void addBook(Book book) {

    }

    public Book getBook(String title) {
        return null;
    }

    public List<Book> getAllBooks() {
        return null;
    }

    public int getBooksCount() {
        return 0;
    }

    public int updateBook(Book book) {
        return 0;
    }

    public void deleteBook(Book book) {

    }

    public void copyDatabase() throws IOException {
        InputStream input = mContext.getAssets().open(DB_NAME);
        String outFile = DB_PATH + DB_NAME;
        OutputStream output = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer))>0){
            output.write(buffer, 0, length);
        }

        //Close the streams
        output.flush();
        output.close();
        input.close();
    }

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

            int i = checkDB.getVersion();
            int a = i;

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public void openDatabase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE" + BK_TABLE + "("
                + KEY_TITLE + " STRING PRIMARY KEY," + KEY_AUTHOR + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mContext.deleteDatabase(DB_NAME);
        onCreate(db);
    }
}
