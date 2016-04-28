package com.vincentxie.book.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EDMOUND on 4/27/2016.
 */
public class Database {
    private DatabaseHelper db_helper;
    private SQLiteDatabase database;

    public final static String BK_TABLE = "Books";
    public final static String BK_TITLE = "title";
    public final static String BK_AUTHOR = "author";

    public Database(Context context) {
        db_helper = new DatabaseHelper(context);
        database = db_helper.getWritableDatabase();
    }

    public long createRecords(String title, String author) {
        ContentValues values = new ContentValues();
        values.put(BK_TITLE, title);
        values.put(BK_AUTHOR, author);
        return database.insert(BK_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {BK_TITLE, BK_AUTHOR};
        Cursor mCursor = database.query(true, BK_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }
}
