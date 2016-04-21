package com.vincentxie.book.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;

public class BookView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Book book = Browse.books.get(getIntent().getIntExtra("index", 0));
        setUpScreen(book);
    }

    /**
     * Goes back to main screen.
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets up screen with book information.
     * @param book
     */
    public void setUpScreen(Book book){
        ImageView cover = (ImageView)findViewById(R.id.book_activity_cover);
        cover.setImageResource(R.drawable.cover);
        setTitle(book.getTitle());
    }

}
