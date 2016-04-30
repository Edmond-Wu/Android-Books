package com.vincentxie.book.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vincentxie.book.model.Book;
import com.vincentxie.book.model.Chapter;
import com.vincentxie.book.model.Genre;

import java.util.*;

/**
 * Created by EDMOUND on 4/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    private static final String DB_NAME = "books.db";

    //tables
    private final static String TABLE_BOOK = "books";
    private final static String TABLE_CHAPTER = "chapters";
    private final static String TABLE_GENRE = "genres";
    private final static String TABLE_BOOK_GENRE = "book_genre";

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

    //genre columns
    private final static String KEY_GENRE = "genre";

    //book_genre columns
    private final static String KEY_BOOK_ID = "book_id";
    private final static String KEY_GENRE_ID = "genre_id";

    //Table creation strings
    private final static String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_TITLE + " TEXT," + KEY_AUTHOR + " TEXT," + KEY_SYNOPSIS + " TEXT," + KEY_COVER + " TEXT" + ")";
    private final static String CREATE_TABLE_CHAPTER = "CREATE TABLE " + TABLE_CHAPTER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_ID + " INTEGER," + KEY_CHAPTER_TITLE + " TEXT," + KEY_DATE + " DATETIME," + KEY_CHAPTER_TEXT + " TEXT" + ")";
    private final static String CREATE_TABLE_GENRE = "CREATE TABLE " + TABLE_GENRE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_GENRE + " TEXT" + ")";
    private final static String CREATE_TABLE_BOOK_GENRE = "CREATE TABLE " + TABLE_BOOK_GENRE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BOOK_ID + " INTEGER," + KEY_GENRE_ID + " INTEGER" + ")";

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public long createBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_SYNOPSIS, book.getSynopsis());
        values.put(KEY_COVER, book.getCover());

        long book_id = db.insert(TABLE_BOOK, null, values);

        List<Genre> genres = getAllGenres();
        List<String> genre_names = new ArrayList<String>();

        //forms a list of genre strings
        for (Genre genre : genres) {
            genre_names.add(genre.getGenre());
        }
        for (Genre genre : book.getGenres()) {
            //if the database contains the genre
            if (genre_names.contains(genre.getGenre())) {
                int genre_index = genre_names.indexOf(genre.getGenre());
                createBookGenre(book_id, genres.get(genre_index).getId());
            }
            else {
                createBookGenre(book_id, createGenre(genre));
            }
        }
        for (Chapter c : book.getChapters()) {
            c.setBookid((int)book_id);
            createChapter(c);
        }
        return book_id;
    }

    public Book getBook(long book_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String select_query = "SELECT * FROM " + TABLE_BOOK + " WHERE " + KEY_ID + " = " + book_id;
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

        List<Chapter> chapters = getChaptersByBook((int)book_id);
        book.getChapters().addAll(chapters);
        List<Genre> genres = getGenresByBook((int)book_id);
        book.getGenres().addAll(genres);

        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        String select_query = "SELECT * FROM " + TABLE_BOOK;

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

                List<Chapter> chapters = getChaptersByBook(book.getId());
                book.getChapters().addAll(chapters);
                List<Genre> genres = getGenresByBook((int)book.getId());
                book.getGenres().addAll(genres);

                books.add(book);
            } while (c.moveToNext());
        }
        return books;
    }

    public List<Book> getAllBooksByAuthor(String author_name) {
        List<Book> books = new ArrayList<Book>();
        String select_query = "SELECT * FROM " + TABLE_BOOK + " WHERE "
                + KEY_AUTHOR + " = \"" + author_name + "\"";

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

                List<Chapter> chapters = getChaptersByBook(book.getId());
                book.getChapters().addAll(chapters);
                List<Genre> genres = getGenresByBook(book.getId());
                book.getGenres().addAll(genres);

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

        List<Chapter> chapters = book.getChapters();
        List<Chapter> db_chapters = getChaptersByBook(book.getId());
        List<String> chapter_names = new ArrayList<String>();
        for (Chapter chapter : db_chapters) {
            chapter_names.add(chapter.getTitle());
        }

        return db.update(TABLE_BOOK, values, KEY_ID + " = ?", new String[]{String.valueOf(book.getId())});
    }

    public void deleteBook(long book_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Chapter> chapters = getChaptersByBook((int)book_id);
        for (Chapter chapter : chapters) {
            System.out.println(chapter.getId());
            deleteChapter(chapter.getId());
        }
        db.delete(TABLE_BOOK, KEY_ID + " = ?", new String[]{String.valueOf(book_id)});
    }

    public long createGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GENRE, genre.getGenre());

        long genre_id = db.insert(TABLE_GENRE, null, values);
        return genre_id;
    }

    public Genre getGenre(long genre_id) {
        Genre genre;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GENRE + " WHERE "
                + KEY_ID + " = " + genre_id;
        Log.e(LOG, query);
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }

        genre = new Genre();
        genre.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        genre.setGenre(c.getString(c.getColumnIndex(KEY_GENRE)));

        return genre;
    }

    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<Genre>();
        String query = "SELECT * FROM " + TABLE_GENRE;
        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Genre genre = new Genre();
                genre.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                genre.setGenre(c.getString(c.getColumnIndex(KEY_GENRE)));
                genres.add(genre);
            } while (c.moveToNext());
        }

        return genres;
    }

    public List<Genre> getGenresByBook(long book_id) {
        List<Genre> genres = new ArrayList<Genre>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK_GENRE + " WHERE "
                + KEY_BOOK_ID + " = " + book_id;
        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Genre genre = new Genre();
                genre.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                genre.setGenre(getGenre(c.getInt(c.getColumnIndex(KEY_GENRE_ID))).getGenre());

                genres.add(genre);
            } while (c.moveToNext());
        }
        return genres;
    }

    public int updateGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GENRE, genre.getGenre());

        return db.update(TABLE_GENRE, values, KEY_ID + " = ?", new String[]{String.valueOf(genre.getId())});
    }

    public void deleteGenre(long genre_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GENRE, KEY_ID + " = ?", new String[]{String.valueOf(genre_id)});
        db.delete(TABLE_BOOK_GENRE, KEY_GENRE_ID + " = ?", new String[]{String.valueOf(genre_id)});
    }


    public long createChapter(Chapter chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_BOOK_ID, chapter.getBookid());
        values.put(KEY_CHAPTER_TITLE, chapter.getTitle());
        values.put(KEY_DATE, chapter.getDatestring());
        values.put(KEY_CHAPTER_TEXT, chapter.getText());
        long chapter_id = db.insert(TABLE_CHAPTER, null, values);
        return chapter_id;
    }

    public Chapter getChapter(long chapter_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CHAPTER + " WHERE "
                + KEY_ID + " = " + chapter_id;
        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        Chapter chapter = new Chapter();
        chapter.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        chapter.setBookid(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
        chapter.setTitle(c.getString(c.getColumnIndex(KEY_CHAPTER_TITLE)));
        chapter.setDatestring(c.getString(c.getColumnIndex(KEY_DATE)));
        chapter.setText(c.getString(c.getColumnIndex(KEY_CHAPTER_TEXT)));

        return chapter;
    }

    public List<Chapter> getAllChapters() {
        List<Chapter> chapters = new ArrayList<Chapter>();
        String query = "SELECT * FROM " + TABLE_CHAPTER;
        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Chapter chap = new Chapter();
                chap.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                chap.setBookid(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
                chap.setTitle(c.getString(c.getColumnIndex(KEY_CHAPTER_TITLE)));
                chap.setText(c.getString(c.getColumnIndex(KEY_CHAPTER_TEXT)));
                chap.setDatestring(c.getString(c.getColumnIndex(KEY_DATE)));
                chap.setDateFromString();

                chapters.add(chap);
            } while (c.moveToNext());
        }

        return chapters;
    }

    public List<Chapter> getChaptersByBook(int book_id) {
        List<Chapter> chapters = new ArrayList<Chapter>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CHAPTER + " WHERE "
                + KEY_BOOK_ID + " = " + book_id;
        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Chapter chap = new Chapter();
                chap.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                chap.setBookid(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
                chap.setTitle(c.getString(c.getColumnIndex(KEY_CHAPTER_TITLE)));
                chap.setText(c.getString(c.getColumnIndex(KEY_CHAPTER_TEXT)));
                chap.setDatestring(c.getString(c.getColumnIndex(KEY_DATE)));
                chap.setDateFromString();

                chapters.add(chap);
            }while (c.moveToNext());
        }
        return chapters;
    }

    public int updateChapter(Chapter chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CHAPTER_TITLE, chapter.getTitle());
        values.put(KEY_CHAPTER_TEXT, chapter.getText());
        values.put(KEY_DATE, chapter.getDatestring());

        return db.update(TABLE_CHAPTER, values, KEY_ID + " = ?", new String[]{String.valueOf(chapter.getId())});
    }

    public void deleteChapter(long chapter_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHAPTER, KEY_ID + " = ?", new String[]{String.valueOf(chapter_id)});
    }

    public long createBookGenre(long book_id, long genre_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_ID, book_id);
        values.put(KEY_GENRE_ID, genre_id);

        long id = db.insert(TABLE_BOOK_GENRE, null, values);
        return id;
    }

    public void printBookGenres() {
        String query = "SELECT * FROM " + TABLE_BOOK_GENRE;
        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                System.out.println("Book ID: " + c.getInt(c.getColumnIndex(KEY_BOOK_ID)) + ", Genre ID: " + c.getInt(c.getColumnIndex(KEY_GENRE_ID)));
            } while (c.moveToNext());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK);
        db.execSQL(CREATE_TABLE_CHAPTER);
        db.execSQL(CREATE_TABLE_GENRE);
        db.execSQL(CREATE_TABLE_BOOK_GENRE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_GENRE);
        onCreate(db);
    }

    public void wipe() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOK, null, null);
        db.delete(TABLE_CHAPTER, null, null);
        db.delete(TABLE_GENRE, null, null);
        db.delete(TABLE_BOOK_GENRE, null, null);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
