package com.vincentxie.book.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.view.MotionEvent;
import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;
import android.view.View.OnTouchListener;
import android.widget.ListAdapter;
import android.view.View.MeasureSpec;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.widget.Button;

import java.util.*;

import com.vincentxie.book.model.Bookmark;
import com.vincentxie.book.model.Chapter;
import com.vincentxie.book.model.Genre;

import android.widget.ListView;
import android.graphics.drawable.Drawable;
import java.io.InputStream;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.RatingBar;
import java.util.ArrayList;

public class BookView extends AppCompatActivity {

    private static Context context;
    ToggleButton subButton;
    private static Book book;
    private BookmarkAdapter bookmarkAdapter;
    private ChapterAdapter chapterAdapter;
    private static ListView bookmarkList;
    private ListView chapters;
    private HashMap<String, Float> ratings = MainMenu.user.getRatings();
    public static List<Bookmark> bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        book = MainMenu.books.get(getIntent().getIntExtra("index", 0));
        Browse.book = book;
        bookmarks = MainMenu.user.getBookmarks().get(book.getTitle() + book.getAuthor());
        context = BookView.this;
        setUpScreen();
        setSubscribeButton();
        setToolbar();
    }

    /**
     * Sets up toolbar.
     */
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(toolbar != null) {
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ScrollView) findViewById(R.id.book_scroll)).smoothScrollTo(0, 0);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }


    @Override
    public void onResume(){
        super.onResume();
        stretchListView(bookmarkList);
        stretchListView(chapters);
        bookmarks = MainMenu.user.getBookmarks().get(book.getTitle() + book.getAuthor());
        bookmarkAdapter.notifyDataSetChanged();
        chapterAdapter.notifyDataSetChanged();
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
    private void setSubscribeButton() {

        subButton = (ToggleButton) findViewById(R.id.sub_button);
        HashMap<String, Boolean> subs = MainMenu.user.getSubscriptions();
        if(subs.get(book.getTitle() + book.getAuthor()) != null){
            if(subs.get(book.getTitle() + book.getAuthor()) == true) {
                subButton.toggle();
            }
        }
        subButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, Boolean> subs = MainMenu.user.getSubscriptions();
                if(subs.get(book.getTitle() + book.getAuthor()) == null || subs.get(book.getTitle() + book.getAuthor()) == false){
                    subs.put(book.getTitle() + book.getAuthor(), true);
                } else {
                    subs.put(book.getTitle() + book.getAuthor(), false);
                }
                MainMenu.user.serialize(getApplicationContext());
            }

        });
        MainMenu.user.serialize(context);
    }

    /**
     * Sets up screen with book information.
     */
    private void setUpScreen(){
        ImageView cover = (ImageView)findViewById(R.id.book_activity_cover);
        addImageFromAssets(cover, book.getCover());
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(book.getTitle());
        TextView author = (TextView)findViewById(R.id.description);
        author.setText(book.getAuthor());
        TextView synopsis = (TextView)findViewById(R.id.synopsis_text);
        synopsis.setText(book.getSynopsis());
        List<Genre> genres = book.getGenres();
        String genresString = "";
        TextView genresText = (TextView)findViewById(R.id.genres_text);
        if(genres != null && genres.size() != 0) {
            for (int i = 0; i < genres.size() - 1; i++) {
                genresString += genres.get(i).getGenre() + ", ";
            }
            genresString += genres.get(genres.size() - 1).getGenre();
        }
        genresText.setText(genresString);
        title.setText(book.getTitle());
        setTitle(book.getTitle());

        setUpChapters(book);
        setUpBookmarks();


        RatingBar ratingBar = ((RatingBar)findViewById(R.id.ratingBar));
        Float rating = ratings.get(book.getTitle() + book.getAuthor());
        if(rating != null) {
            ratingBar.setRating(rating);
        } else {
            ratingBar.setRating(0);
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratings.put(book.getTitle() + book.getAuthor(), v);
            }
        });
        MainMenu.user.serialize(context);
    }

    private void setUpBookmarks(){
        bookmarkList = (ListView) findViewById(R.id.bookmarks);
        if(bookmarks == null){
            bookmarks = new ArrayList<Bookmark>();
        }
        bookmarkAdapter = new BookmarkAdapter(this, R.layout.bookmark_row, bookmarks);
        if (bookmarkList != null) {
            bookmarkList.setAdapter(bookmarkAdapter);
            stretchListView(bookmarkList);
            bookmarkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(id);
                    Intent myIntent = new Intent(getApplicationContext(), Reader.class);
                    int index = bookmarks.get(position).getChapter();
                    myIntent.putExtra("bookmarks", position);
                    myIntent.putExtra("index", index);
                    startActivity(myIntent);
                }
            });
        }
    }

    /**
     * Sets up listview with chapters.
     * @param book
     */
    private void setUpChapters(Book book){

        chapters = (ListView) findViewById(R.id.chapters);
        if (chapters != null) {
            chapters.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
        }

        chapterAdapter = new ChapterAdapter(this, R.layout.row, book.getChapters());
        if (chapters != null) {
            chapters.setAdapter(chapterAdapter);
            stretchListView(chapters);
            chapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(getApplicationContext(), Reader.class);
                    myIntent.putExtra("index", position);
                    startActivity(myIntent);
                }
            });
        }
        //DatabaseHelper db_helper = new DatabaseHelper(context);
        //db_helper.updateBook(book);
        //book.toJson(context);
    }

    /**
     * Stretches listview to show all elements.
     * @param listView
     */
    public static void stretchListView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private static class ChapterAdapter extends ArrayAdapter<Chapter> {

        Context context;
        List<Chapter> chapters;
        int rowid;

        private static class ViewHolder {
            private TextView itemView;
        }

        public ChapterAdapter(Context context, int resourceId, List<Chapter> items) {
            super(context, -1, items);
            this.chapters = items;
            this.context = context;
            this.rowid = resourceId;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(rowid, parent, false);
            TextView titleView = (TextView) row.findViewById(R.id.title);
            titleView.setText(chapters.get(position).getTitle());

            return row;
        }
    }

    private static class BookmarkAdapter extends ArrayAdapter<Bookmark> {

        Context context;
        List<Bookmark> bookmarks;
        int rowid;

        private static class ViewHolder {
            private TextView itemView;
        }

        public BookmarkAdapter(Context context, int resourceId, List<Bookmark> items) {
            super(context, -1, items);
            this.bookmarks = items;
            this.context = context;
            this.rowid = resourceId;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(rowid, parent, false);
            TextView titleView = (TextView) row.findViewById(R.id.title);
            titleView.setText(bookmarks.get(position).getName());
            ImageView delete = (ImageView) row.findViewById(R.id.delete_bookmark_button);
            delete.setTag(position);
            delete.setOnClickListener(
                    new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final View myView = view;
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Integer index = (Integer) myView.getTag();
                                    bookmarks.remove(index.intValue());
                                    stretchListView(bookmarkList);
                                    notifyDataSetChanged();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {}
                            });
                            builder.setTitle("Delete this bookmark?");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
            );

            return row;
        }
    }
}
