package com.vincentxie.book.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Book;
import com.vincentxie.book.model.Update;

import java.io.InputStream;
import java.util.List;
import com.vincentxie.book.model.User;

/**
 * Created by vincexie on 4/19/16.
 */
public class Home extends Fragment {

    private UpdateAdapter adapter;
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        setUpUpdateList(view, MainMenu.user);
        return view;
    }

    public void setUpUpdateList(View view, User user){
        adapter = new UpdateAdapter(getActivity(), R.layout.browse_listitem, user.getUpdates());
        list = (ListView) view.findViewById(R.id.updates);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(), BookView.class);
                myIntent.putExtra("index", position);
                startActivity(myIntent);
            }
        });
    }

    private static class UpdateAdapter extends ArrayAdapter<Update> {

        Context context;
        List<Update> updates;
        int rowID;

        private static class ViewHolder {
            private TextView itemView;
        }

        public UpdateAdapter(Context context, int rowId, List<Update> items) {
            super(context, -1, items);
            this.updates = items;
            this.context = context;
            this.rowID = rowId;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(rowID, parent, false);
            TextView titleView = (TextView) row.findViewById(R.id.title);
            titleView.setText(updates.get(position).getUpdate());
            TextView authorView = (TextView) row.findViewById(R.id.author);
            authorView.setText(updates.get(position).getUpdate());

            ImageView cover = (ImageView) row.findViewById(R.id.cover);
            try
            {
                InputStream is = context.getAssets().open("cover.jpg");
                Drawable d = Drawable.createFromStream(is, null);
                cover.setImageDrawable(d);
                is.close();
            }
            catch(Exception e){}
            return row;
        }
    }
}