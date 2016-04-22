package com.vincentxie.book.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;

import java.util.List;
import com.vincentxie.book.model.Chapter;
import android.widget.ListView;

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

        ListView myListView = (ListView) findViewById(R.id.chapters);
        ChapterAdapter adapter = new ChapterAdapter(this, R.layout.row, book.getChapters());
        myListView.setAdapter(adapter);

        setTitle(book.getTitle());
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
