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
import java.util.*;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import java.text.SimpleDateFormat;

/**
 * Created by vincexie on 4/19/16.
 */
public class Home extends Fragment {

    private UpdateAdapter adapter;
    private ListView list;
    private boolean isLoading = false;
    private List<Update> updates;
    private List<Update> updates_master;
    private final int INITIAL_SIZE = 5;
    private View loading;

    private Runnable loadUpdates = new Runnable() {
        @Override
        public synchronized void run() {
            isLoading = true;
            try { Thread.sleep(750);
            } catch (InterruptedException e) {}
                if(MainMenu.books.size() > 0){
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


    /**
     * Prunes update list for unsubscribed books.
     */
    private void pruneList(){
        HashMap<String, Boolean> subs = MainMenu.user.getSubscriptions();
        for(int i = 0; i < updates_master.size(); i++){
            Boolean sub = subs.get(updates_master.get(i).getBook().getTitle() + updates_master.get(i).getBook().getAuthor());
            if(sub == null || sub == false){
                updates_master.set(i, null);
            }
        }
    }

    /**
     * Set up list of updates
     * @param view
     */
    private void setUpUpdateList(View view){
        list = (ListView) view.findViewById(R.id.updates);
        pruneList();
        while(updates_master.remove(null));
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

            getActivity().findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.smoothScrollToPosition(0);
                }
            });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(getActivity().getApplicationContext(), BookView.class);
                    myIntent.putExtra("index", MainMenu.books.indexOf(updates.get(position).getBook()));
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
            TextView time = (TextView) row.findViewById(R.id.time);
            time.setText(new SimpleDateFormat("MM-dd-yyyy hh:mm a").format(updates.get(position).getTime()));

            ImageView cover = (ImageView) row.findViewById(R.id.cover);
            try
            {
                InputStream is = context.getAssets().open(updates.get(position).getBook().getCover());
                Drawable d = Drawable.createFromStream(is, null);
                cover.setImageDrawable(d);
                is.close();
            }
            catch(Exception e){}
            return row;
        }
    }
}