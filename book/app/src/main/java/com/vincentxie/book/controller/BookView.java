package com.vincentxie.book.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;

import java.io.File;
import java.util.List;
import com.vincentxie.book.model.Chapter;
import android.widget.ListView;
import android.graphics.drawable.Drawable;
import java.io.InputStream;

public class BookView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Book book = Browse.books.get(getIntent().getIntExtra("index", 0));
        Browse.book = book;
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
     * Adds image from assets folder to imageview
     * @param imageView image view
     * @param name name of file
     */
    public void addImageFromAssets(ImageView imageView, String name){
        try
        {
            InputStream is = getAssets().open(name);
            Drawable d = Drawable.createFromStream(is, null);
            imageView.setImageDrawable(d);
            is.close();
        }
        catch(Exception e)
        {
            return;
        }
    }

    /**
     * Sets up screen with book information.
     * @param book
     */
    public void setUpScreen(Book book){
        ImageView cover = (ImageView)findViewById(R.id.book_activity_cover);
        addImageFromAssets(cover, "cover.jpg");
        setUpChapters(book);
        setTitle(book.getTitle());
    }

    /**
     * Sets up listview with chapters.
     * @param book
     */
    private void setUpChapters(Book book){

        book.serialize();

        ListView chapters = (ListView) findViewById(R.id.chapters);
        ChapterAdapter adapter = new ChapterAdapter(this, R.layout.row, book.getChapters());
        chapters.setAdapter(adapter);

        chapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getApplicationContext(), Reader.class);
                myIntent.putExtra("index", position);
                startActivity(myIntent);
            }
        });
    }

    private static class ChapterAdapter extends ArrayAdapter<Chapter> {

        Context context;
        List<Chapter> chapters;

        private static class ViewHolder {
            private TextView itemView;
        }

        public ChapterAdapter(Context context, int textViewResourceId, List<Chapter> items) {
            super(context, -1, items);
            this.chapters = items;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView titleView = (TextView) row.findViewById(R.id.title);
            titleView.setText(chapters.get(position).getChapterTitle());

            return row;
        }
    }

}
