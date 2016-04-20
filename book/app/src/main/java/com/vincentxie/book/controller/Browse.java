package com.vincentxie.book.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vincentxie.book.model.Book;
import android.content.Context;
import android.widget.TextView;
import android.widget.Spinner;

import java.io.File;
import java.util.*;

import com.vincentxie.book.R;


/**
 * Created by vincexie on 4/19/16.
 */
public class Browse extends Fragment {

    List<Book> books = new ArrayList<Book>();
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public Browse() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.browse, container, false);

        books.add(new Book("title", "author", "test", new File("")));
        books.add(new Book("title2", "author2", "test2", new File("")));
        books.add(new Book("title", "author", "test", new File("")));
        books.add(new Book("title2", "author2", "test2", new File("")));
        books.add(new Book("title", "author", "test", new File("")));
        books.add(new Book("title2", "author2", "test2", new File("")));
        books.add(new Book("title", "author", "test", new File("")));
        books.add(new Book("title2", "author2", "test2", new File("")));
        books.add(new Book("title", "author", "test", new File("")));
        books.add(new Book("title2", "author2", "test2", new File("")));
        books.add(new Book("title", "author", "test", new File("")));
        books.add(new Book("title2", "author2", "test2", new File("")));

        BookAdapter adapter = new BookAdapter(getActivity(), R.layout.browse_listitem, books);
        list = (ListView) view.findViewById(R.id.book_list);
        list.setAdapter(adapter);

        return view;
    }

    private static class BookAdapter extends ArrayAdapter<Book> {

        Context context;
        List<Book> books;

        private static class ViewHolder {
            private TextView itemView;
        }

        public BookAdapter(Context context, int textViewResourceId, List<Book> items) {
            super(context, -1, items);
            this.books = items;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.browse_listitem, parent, false);
            TextView titleView = (TextView) row.findViewById(R.id.title);
            titleView.setText(books.get(position).getTitle());
            TextView authorView = (TextView) row.findViewById(R.id.author);
            authorView.setText(books.get(position).getAuthor());

            return row;
        }
    }

}