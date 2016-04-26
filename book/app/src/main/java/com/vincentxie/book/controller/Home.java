package com.vincentxie.book.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.app.Activity;
import java.util.ArrayList;

/**
 * Created by vincexie on 4/19/16.
 */
public class Home extends Fragment {

    private UpdateAdapter adapter;
    private ListView list;
    private boolean isLoading = false;
    private List<Update> updates;
    private List<Update> updates_master;
    private Activity activity = getActivity();
    private int index;
    private final int INITIAL_SIZE = 5;
    private View loading;

    private Runnable loadUpdates = new Runnable() {
        @Override
        public synchronized void run() {
            isLoading = true;
            try { Thread.sleep(750);
            } catch (InterruptedException e) {}
                if(Browse.books.size() > 0){
                    int size = updates.size();
                    for(int i = size; i < updates_master.size() && i < size + 5; i++) {
                        updates.add(updates_master.get(i));
                    }
                }
            if(getActivity() != null) {
                getActivity().runOnUiThread(updateList);
            }
        }
    };

    private Runnable updateList = new Runnable() {
        @Override
        public synchronized void run() {
            adapter.notifyDataSetChanged();
            isLoading = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        updates_master = MainMenu.user.getUpdates();
        super.onCreate(savedInstanceState);
        index = 5;
    }

    public Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        setUpUpdateList(view);
        return view;
    }

    public void setUpUpdateList(View view){
        list = (ListView) view.findViewById(R.id.updates);
        if(updates_master.size() > 0) {
            updates = new ArrayList<Update>();
            for(int i = 0; i < INITIAL_SIZE && i < updates_master.size(); i++){
                updates.add(updates_master.get(i));
            }
            adapter = new UpdateAdapter(getActivity(), R.layout.home_updateitem, updates);
            loading = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading, null, false);
            list.addFooterView(loading);
            list.setAdapter(adapter);

            list.setOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int first, int visible, int total) {
                    int last = first + visible;
                    if ((last == total) && !(isLoading)) {
                        Thread thread = new Thread(null, loadUpdates);
                        thread.start();
                    }
                    if(total >= updates_master.size()){
                        list.removeFooterView(loading);
                    }
                }
            });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(getActivity().getApplicationContext(), BookView.class);
                    myIntent.putExtra("index", position);
                    startActivity(myIntent);
                }
            });
        } else {
            updates = new ArrayList<Update>();
            adapter = new UpdateAdapter(getActivity(), R.layout.home_updateitem, updates);
            View footer = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.no_updates, null, false);
            list.addFooterView(footer);
            list.setAdapter(adapter);
        }
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
            TextView descView = (TextView) row.findViewById(R.id.description);
            descView.setText(updates.get(position).getDescription());

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