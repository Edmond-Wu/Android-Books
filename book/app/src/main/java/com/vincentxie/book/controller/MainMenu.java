package com.vincentxie.book.controller;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.content.Context;

import com.vincentxie.book.*;
import com.vincentxie.book.model.Book;

import android.content.Intent;
import android.widget.TextView;
import android.content.SharedPreferences;
import com.vincentxie.book.model.User;

import java.io.Serializable;
import java.util.*;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SearchView searchView = null;
    public static User user;
    private List<Book> books = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        setName(drawer);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = new User("","");
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            setFragment(Home.class, "Home", bundle);
        } else {
            deselectAll();
        }
    }

    /**
     * Sets fragment.
     * @param fragmentClass
     */
    private void setFragment(Class fragmentClass, String title, Bundle bundle){
        Fragment fragment = null;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment, "fragment").commit();
        fragment.setArguments(bundle);
        setTitle(title);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText.length() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        setFragment(Search.class, "Results", bundle);
                        deselectAll();
                        search(newText);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    setFragment(Search.class, "Results for " + "'" + query + "'", bundle);
                    searchView.onActionViewCollapsed();
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Gets a list of books based on a search query
     * @param query String keyword
     * @return a list of books relevant to that keyword
     */
    public void search(String query) {
        query = query.toLowerCase();
        String[] queries = query.split(" ");
        Search.results = new ArrayList<Book>();
        for(Book b: books){
            Search.results.add(b);
        }
        List<Book> results = Search.results;
        for(String q: queries){
            for(int i = 0; i < results.size(); i++){
                String title = results.get(i).getTitle().toLowerCase();
                String author = results.get(i).getAuthor().toLowerCase();
                if(!title.contains(q) && !author.contains(q)){
                    results.remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Deselects everything in the submenu.
     */
    private void deselectSubmenu(){
        NavigationView menu = (NavigationView) findViewById(R.id.nav_view);
        Menu m = menu.getMenu().getItem(3).getSubMenu();
        for(int i = 0; i < m.size(); i++){
            m.getItem(i).setChecked(false);
        }
    }

    /**
     * Deselects everything in the menu.
     */
    private void deselectAll() {
        NavigationView menu = (NavigationView) findViewById(R.id.nav_view);
        menu.setCheckedItem(R.id.invis);
        deselectSubmenu();
    }

    /**
     * Sets display name when drawer changes.
     */
    private void setName(DrawerLayout drawer){
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {}

            @Override
            public void onDrawerOpened(View view) {}

            @Override
            public void onDrawerClosed(View view) {}

            @Override
            public void onDrawerStateChanged(int i) {
                searchView.onActionViewCollapsed();
                setNameUtil();
            }
        });
    }

    /**
     * Sets display name.
     */
    private void setNameUtil(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPref.getString("name", "");
        TextView nameView = (TextView) findViewById(R.id.display_name);
        nameView.setText(name);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Bundle bundle;
        switch (item.getItemId()){
            case R.id.nav_home:
                bundle = new Bundle();
                bundle.putSerializable("books", (Serializable)books);
                bundle.putSerializable("user", user);
                setFragment(Home.class, "Home", bundle);
                break;
            case R.id.nav_subscribed:
                bundle = new Bundle();
                bundle.putSerializable("books", (Serializable)books);
                bundle.putSerializable("user", user);
                setFragment(Subscribed.class, "Subscribed", bundle);
                break;
            case R.id.nav_browse:
                bundle = new Bundle();
                bundle.putSerializable("books", (Serializable)books);
                bundle.putSerializable("user", user);
                setFragment(Browse.class, "Browse", bundle);
                break;
            /* case R.id.nav_profile:
                setFragment(Profile.class, "Profile");
                break; */
            case R.id.nav_settings:
                startActivity(new Intent(this, Settings.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
