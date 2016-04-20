package com.vincentxie.book.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vincentxie.book.R;

/**
 * Created by vincexie on 4/19/16.
 */
public class Browse extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public Browse() {

    }

    public void sortTitles(View view) {
        System.out.println("Test");
    }

    public void sortAuthors(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.browse, container, false);
    }
}