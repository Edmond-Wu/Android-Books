package com.vincentxie.book.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by vincexie on 4/19/16.
 */
public class Search extends Fragment {

    public static List<Book> results = new ArrayList<Book>();
    private BookAdapter adapter;
    private ListView list;
    private static HashMap<Book, Float> ratings = MainMenu.user.getRatings();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public Search() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        setUpSearch(view);
        return view;
    }

    /**
     * Set up searchList.
     * @param view
     */
    private void setUpSearch(View view){
        adapter = new BookAdapter(getActivity(), R.layout.browse_listitem, results);
        list = (ListView) view.findViewById(R.id.search_results);
        list.setAdapter(adapter);

        getActivity().findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.smoothScrollToPosition(0);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(), BookView.class);
                myIntent.putExtra("index", Browse.books.indexOf(results.get(position)));
                startActivity(myIntent);
            }
        });

        view.findViewById(R.id.search_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private static class BookAdapter extends ArrayAdapter<Book> {

        Context context;
        List<Book> books;
        int rowID;

        private static class ViewHolder {
            private TextView itemView;
        }

        public BookAdapter(Context context, int rowId, List<Book> items) {
            super(context, -1, items);
            this.books = items;
            this.context = context;
            this.rowID = rowId;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(rowID, parent, false);
            TextView titleView = (TextView) row.findViewById(R.id.title);
            titleView.setText(books.get(position).getTitle());
            TextView authorView = (TextView) row.findViewById(R.id.description);
            authorView.setText(books.get(position).getAuthor());
            RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rating_browse_bar);
            Float rating = ratings.get(books.get(position));
            if(rating != null) {
                ratingBar.setRating(rating);
            }

            ImageView cover = (ImageView) row.findViewById(R.id.cover);
            try
            {
                InputStream is = context.getAssets().open(books.get(position).getCover());
                Drawable d = Drawable.createFromStream(is, null);
                cover.setImageDrawable(d);
                is.close();
            }
            catch(Exception e){}
            return row;
        }
    }
}