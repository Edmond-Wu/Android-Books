package com.vincentxie.book.controller;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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
import com.vincentxie.book.database.DatabaseHelper;
import com.vincentxie.book.model.Book;

import android.content.Intent;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.vincentxie.book.model.Chapter;
import com.vincentxie.book.model.Genre;
import com.vincentxie.book.model.Update;
import com.vincentxie.book.model.User;

import java.util.*;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SearchView searchView = null;
    public static User user;
    private static Context context;
    public static List<Book> books = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = MainMenu.this;
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

        if (savedInstanceState == null) {
            setFragment(Home.class, "Home");
        } else {
            deselectAll();
        }

        user = new User("", "");
        user.toJson(context);

        /*
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre("Action"));
        genres.add(new Genre("Adventure"));
        Book bk = new Book("Z", "A", genres, "The change brought to entertain the bored God. And the story of Kang Hansoo who returned to the past to save the humankind from its perishment brought by the change.", "reincarnator.jpg");
        Book bk1 = new Book("Reincarnator", "ALLA", genres, "The change brought to entertain the bored God. And the story of Kang Hansoo who returned to the past to save the humankind from its perishment brought by the change.", "reincarnator.jpg");
        Chapter chapter = new Chapter("Bk Chapter 1", "A god who loved watching bloody battles the most created a new world to get rid of his boredom.\n" +
                "\n" +
                "Fight and kill, a reward will be given.\n" +
                "\n" +
                "If you are lazy, you will die.\n" +
                "\n" +
                "The god named the bizarre world <Abyss> and started to slowly pour in the life forms he created.\n" +
                "\n" +
                "****************\n" +
                "\n" +
                "“As I expected, only one person can go back to the past. Do we need to have a popularity vote?”\n" +
                "Keldian, one of the four people who were standing in front of the crystal and who had a golden book, muttered while looking around the crystal.\n" +
                "\n" +
                "<Erkanian’s Time Space Crystal>\n" +
                "A great tool that is said to have the mythical power of sending one to the past.\n" +
                "And the last remaining hope of the humankind which entered the Abyss 50 years ago and perished.\n" +
                "\n" +
                "A man in the corner with a massive sword spoke out at those words.\n" +
                "“I’m going. The strongest person should go.”\n" +
                "Keldian laughed at those words.\n" +
                "“Kangtae, you’ve just been lucky and gotten strong by stuffing runes and items. It’ll be much better if I go.”\n" +
                "“Keldian, I acknowledge your intellect but there were numerous occasions of disagreements when you clowned around with it. I shall go instead.”\n" +
                "Keldian stared at Eres and then laughed.\n" +
                "“Eres, you’re too naive. Thinking of the troubles underneath you, you disqualify as well.”\n" +
                "\n" +
                "The three who were arguing stopped and stared into the distance.\n" +
                "Huge dragons that showed off their massive bodies were flying with incredible speed.\n" +
                "The temple in which the crystal resided and the woman who saw the real owners of the crystals, Eres, spoke out with a bittersweet face.\n" +
                "“It seems like it isn’t the time to argue.”\n" +
                "\n" +
                "The fact that the dragons were flying here means that the forces they used to buy time have all been annihilated.\n" +
                "If those had died, these 4 were the last of mankind.\n" +
                "The golden dragon race was one of the top ruling classes even in the Abyss.\n" +
                "They weren’t opponents for the dragons as they struggled just to get this far.\n" +
                "\n" +
                "Eres sighed with a regrettable face and spoke while looking at the man with a black hair sitting in the corner.\n" +
                "“Although it feels a little unfair there’s no other way. Hansoo, you go. Everyone agrees?”\n" +
                "At those words Kangtae and Keldian held a reluctant face then sighed as well.\n" +
                "“Can I really not go? I have confidence I can do well.”\n" +
                "“…”\n" +
                "“Alright. Don’t look at me like that. Petty ones.”\n" +
                "Kangtae complained with an extremely pitiful face.\n" +
                "Then Hansoo sighed with a tired look.\n" +
                "“Can’t I stop fighting now?”\n" +
                "Hansoo shook his head.\n" +
                "\n" +
                "50 years since the great war between the original races who had been dragged into the Abyss.\n" +
                "The survivors had to fight rigorously every day for 50 years.\n" +
                "\n" +
                "Just to survive.\n" +
                "\n" +
                "“I’ve fought too long.”\n" +
                "Hansoo shook his head.\n" +
                "As if it’s allright to die like this.\n" +
                "But Eres’ head shook with a firm look.\n" +
                "“You are the one who has to go.”\n" +
                "The four people here including herself arrived this far because they were the most outstanding of the 7 billion people.\n" +
                "They had confidence to do better if they returned to the past and to do so they needed another chance.\n" +
                "But everyone knew inside.\n" +
                "‘He’s the one who has to go.’\n" +
                "\n" +
                "The ruler classes of abyss were so strong that even if they went back in time they didn’t have a complete guarantee that they could win against them.\n" +
                "However, he started 20 years after they did yet he stood shoulder to shoulder with them.\n" +
                "If he had bloomed his unique potential a little earlier, no, 5 years earlier, then they wouldn’t have been pushed back this far.\n" +
                "\n" +
                "Hansoo looked at the three and then spoke out.\n" +
                "“Say something, I should at least listen to the last words of my friends.”\n" +
                "If it was anybody else it wouldn’t have mattered but how could he ignore their words.\n" +
                "As Hansoo watched the three with a regrettable look, Kangtae spoke out first.\n" +
                "“You. If you have the chance to acquire my runes and items, take them all and use them.”\n" +
                "“Huh? Don’t I give them to the you of the past?”\n" +
                "Hansoo asked with a surprised face.\n" +
                "His items and runes were immeasurable to the point that his nickname was fate creator.\n" +
                "To the point where a huge problem between them occurred.\n" +
                "“Yeah, it’s better that you use them instead of me. If you’re going to do something, do it right.”\n" +
                "Hansoo nodded at those words.\n" +
                "\n" +
                "“Eres, what about you?”\n" +
                "“Don’t fling off people who approach you just because it’s annoying and take care of them.”\n" +
                "“I will try.”\n" +
                "“Oh come on, you’re going to save humanity aren’t you? Think about how cool it is. Listen to the leader.”\n" +
                "“Well, if the situation allows it.”\n" +
                "“Sigh..”\n" +
                "\n" +
                "Hansoo turned his back to the sighing Eres as he asked Keldian last:\n" +
                "“Keldian, what about you? Oh by the way I have no confidence in using my brain as well as you. I have no confidence in collecting all the skills you use either.”\n" +
                "Keldian replied with a cold face:\n" +
                "“I don’t have much. If you go back to the past… get rid of those ‘cockroaches’ who will only be of harm during the great war. And that Mad Lord, kill him for sure. That is my request.”\n" +
                "Hansoo nodded and Keldian smiled with a satisfied smile. Then he lifted up his book and started mumbling something.\n" +
                "\n" +
                "Then shining light bursted out of the crystal and surrounded Hansoo as he disappeared with the light.\n" +
                "“I should rest now.”\n" +
                "They wanted to go but they also wanted to rest.\n" +
                "They didn’t know their true feelings so they said no in case they regretted it.\n" +
                "Since this chance is literally the last chance.\n" +
                "However since it was decided and he was sent off, they clearly knew their true feelings.\n" +
                "Since their minds were now at rest.\n" +
                "At the same time they felt bad for Hansoo.\n" +
                "“Take care. We leave it up to you.”\n" +
                "The three watched the disappeared Hansoo as they smiled with a mix of regret and relief. Soon the energy blasted out by the golden dragons swept them from above like a storm.", bk.getId());
        bk.getChapters().add(chapter);
        bk.getChapters().add(new Chapter("Bk Chapter 2", "This is Chapter 2.", bk.getId()));
        bk1.getChapters().add(new Chapter("Bk1 Chapter 1", "This is Chapter 1.", bk1.getId()));
        books.add(bk);
        books.add(bk1);
        */
        DatabaseHelper db = new DatabaseHelper(context);
        List<Genre> genres = db.getAllGenres();
        for (Genre genre : genres) {
            System.out.println(genre.getGenre());
        }
        for (Book book : db.getAllBooks()) {
            System.out.println(book.getTitle());
        }
    }

    /**
     * Sets fragment.
     * @param fragmentClass
     */
    private void setFragment(Class fragmentClass, String title){
        Fragment fragment = null;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment, "fragment").commit();
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
                        setFragment(Search.class, "Results");
                        deselectAll();
                        search(newText);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    setFragment(Search.class, "Results for " + "'" + query + "'");
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
        user.setUsername(name);
        user.toJson(context);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_home:
                setFragment(Home.class, "Home");
                break;
            case R.id.nav_subscribed:
                setFragment(Subscribed.class, "Subscribed");
                break;
            case R.id.nav_browse:
                setFragment(Browse.class, "Browse");
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
