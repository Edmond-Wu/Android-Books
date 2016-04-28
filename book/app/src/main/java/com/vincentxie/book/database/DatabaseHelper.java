package com.vincentxie.book.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.*;

/**
 * Created by EDMOUND on 4/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static Context mContext;
    private static String db_path = "/data/data/vincentxie.book/databases/";
    private static final String DB_NAME = "books.db";
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

    public void copyDatabase() throws IOException {
        InputStream input = mContext.getAssets().open(DB_NAME);
        String outFile = db_path + DB_NAME;
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
            String myPath = db_path + DB_NAME;
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
        String path = db_path + DB_NAME;
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mContext.deleteDatabase(DB_NAME);
    }
}
