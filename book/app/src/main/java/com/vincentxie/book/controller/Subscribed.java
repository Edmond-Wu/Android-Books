package com.vincentxie.book.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;

/**
 * Created by vincexie on 4/19/16.
 */
public class Subscribed extends Fragment {

    private List<Book> books = new ArrayList<Book>();
    private BookAdapter adapter;
    private String currentSort;
    private ListView list;
    private static Context context1;
    private static HashMap<Book, Float> ratings = MainMenu.user.getRatings();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public Subscribed() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browse, container, false);
        setList();
        setUpList(view);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        setList();
        if(currentSort.equals("title")){
            books = com.vincentxie.book.util.Sorter.sortByTitle(books);
        } else if (currentSort.equals("author")){
            books = com.vincentxie.book.util.Sorter.sortByAuthor(books);
        } else if (currentSort.equals("rating")){
            books = com.vincentxie.book.util.Sorter.sortByRating(ratings, books);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Sets list
     */
    public void setList(){
        books.clear();
        HashMap<Book, Boolean> subs = MainMenu.user.getSubscriptions();
        for(Book b: subs.keySet()){
            if(subs.get(b) == true){
                books.add(b);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context1 = context;
    }

    /**
     * Set up spinner to sort list.
     * @param view
     */
    private void setSpinner(View view){
        Spinner sortby = (Spinner)view.findViewById(R.id.sortby);

        sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(selectedItemView instanceof AppCompatTextView){
                    AppCompatTextView text = (AppCompatTextView)selectedItemView;
                    CharSequence select = text.getText();
                    if(select.equals(currentSort)){
                        return;
                    }
                    if(select.equals("title")){
                        books = com.vincentxie.book.util.Sorter.sortByTitle(books);
                        currentSort = "title";
                    } else if(select.equals("author")) {
                        books = com.vincentxie.book.util.Sorter.sortByAuthor(books);
                        currentSort = "author";
                    } else if(select.equals("rating")) {
                        books = com.vincentxie.book.util.Sorter.sortByRating(ratings, books);
                        currentSort = "rating";
                    }
                    if(adapter != null){
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
    }

    /**
     * Deserializes book info from a file
     * @param file_name name of the file
     * @param context Context
     * @return a Book object
     */
    public Book deserialize(String file_name, Context context) {
        Book book = null;
        FileInputStream fileIn;
        try {
            fileIn = context.openFileInput(file_name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            book = (Book) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    /**
     * Gets the book from a json file
     * @param file_name
     * @param context
     * @return
     */
    public Book jsonDeserialize(String file_name, Context context){
        Book b = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            b = mapper.readValue(context.openFileInput(file_name), Book.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Sets up listview with booklist.
     * @param view
     */
    private void setUpList(View view){
        for (Book b : books) {
            //b.serialize(context1);
            b.toJson(context1);
        }


        /* File folder = context1.getFilesDir();
        File[] directoryListing = folder.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String file_name = child.getName();
                if (file_name.contains("book-")) {
                    Book b = jsonDeserialize(file_name, context1);
                    System.out.println(b.getTitle());
                }
            }
        } else {
            System.out.println("Empty or invalid directory");
        } */

        currentSort = "title";
        books = com.vincentxie.book.util.Sorter.sortByTitle(books);
        adapter = new BookAdapter(getActivity(), R.layout.browse_listitem, books);
        list = (ListView) view.findViewById(R.id.book_list);
        list.setAdapter(adapter);
        setSpinner(view);

        getActivity().findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.smoothScrollToPosition(0);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(), BookView.class);
                int index = Browse.books.indexOf(books.get(position));
                myIntent.putExtra("index", index);
                startActivity(myIntent);
            }
        });
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