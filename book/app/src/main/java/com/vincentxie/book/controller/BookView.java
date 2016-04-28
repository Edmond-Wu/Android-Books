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
import android.widget.ScrollView;
import android.widget.TextView;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import com.vincentxie.book.model.Chapter;
import android.widget.ListView;
import android.graphics.drawable.Drawable;
import java.io.InputStream;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class BookView extends AppCompatActivity {

    private static Context context;
    ToggleButton subButton;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        book = Browse.books.get(getIntent().getIntExtra("index", 0));
        context = BookView.this;
        setUpScreen(book);
        setSubscribeButton();
        Browse.book = book;
        setToolbar();
    }

    /**
     * Sets up toolbar.
     */
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScrollView)findViewById(R.id.book_scroll)).smoothScrollTo(0, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
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
     * Add listener to subscribe button
     */
    public void setSubscribeButton() {

        subButton = (ToggleButton) findViewById(R.id.sub_button);
        HashMap<Book, Boolean> subs = MainMenu.user.getSubscriptions();
        if(subs.get(book) != null){
            if(subs.get(book) == true) {
                subButton.toggle();
            }
        }
        subButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<Book, Boolean> subs = MainMenu.user.getSubscriptions();
                if(subs.get(book) == null || subs.get(book) == false){
                    subs.put(book, true);
                } else {
                    subs.put(book, false);
                }
            }

        });

    }

    /**
     * Sets up screen with book information.
     * @param book
     */
    public void setUpScreen(Book book){
        ImageView cover = (ImageView)findViewById(R.id.book_activity_cover);
        addImageFromAssets(cover, "cover.jpg");
        setUpChapters(book);
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(book.getTitle());
        TextView author = (TextView)findViewById(R.id.description);
        author.setText(book.getAuthor());
        TextView synopsis = (TextView)findViewById(R.id.synopsis_text);
        synopsis.setText(book.getSynopsis());
        List<String> genres = book.getGenre();
        String genresString = "";
        TextView genresText = (TextView)findViewById(R.id.genres_text);
        if(genres != null && genres.size() != 0) {
            for (int i = 0; i < genres.size() - 1; i++) {
                genresString += genres.get(i) + ", ";
            }
            genresString += genres.get(genres.size() - 1);
        }
        genresText.setText(genresString);
        title.setText(book.getTitle());
        setTitle(book.getTitle());

        /* getSupportActionBar().getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScrollView)findViewById(R.id.book_scroll)).smoothScrollTo(0, 0);
            }
        }); */
    }

    /**
     * Sets up listview with chapters.
     * @param book
     */
    private void setUpChapters(Book book){

        ListView chapters = (ListView) findViewById(R.id.chapters);
        ChapterAdapter adapter = new ChapterAdapter(this, R.layout.row, book.getChapters());
        chapters.setAdapter(adapter);

        book.toJson(context);

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
            titleView.setText(chapters.get(position).getTitle());

            return row;
        }
    }
}
