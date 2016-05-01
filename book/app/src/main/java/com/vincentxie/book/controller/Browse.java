package com.vincentxie.book.controller;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentxie.book.database.DatabaseHelper;
import com.vincentxie.book.model.Book;
import android.content.Context;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.io.*;
import java.util.*;

import com.vincentxie.book.R;
import com.vincentxie.book.model.Chapter;
import com.vincentxie.book.model.Genre;
import com.vincentxie.book.model.Update;

import android.widget.AdapterView.OnItemSelectedListener;
import android.util.AttributeSet;
import com.vincentxie.book.model.User;

/**
 * Created by vincexie on 4/19/16.
 */
public class Browse extends Fragment {

    ListView list;
    public static Book book;
    private BookAdapter adapter;
    private String currentSort;
    private static Context context1;
    private static HashMap<Book, Float> ratings;
    private User user;
    public static List<Book> books;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = ((User) getArguments().getSerializable("user"));
        ratings = user.getRatings();
        books = ((List<Book>) getArguments().getSerializable("books"));
    }
    public Browse() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browse, container, false);
        setUpList(view, books);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context1 = context;
    }

    public static class MySpinner extends Spinner {

        OnItemSelectedListener listener;

        public MySpinner(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setSelection(int position) {
            super.setSelection(position);

            if (position == getSelectedItemPosition()) {
                listener.onItemSelected(null, null, position, 0);
            }
        }

        public void setOnItemSelectedListener(OnItemSelectedListener listener) {
            this.listener = listener;
        }
    }


            /**
             * Set up spinner to sort list.
             * @param view
             */
    private void setSpinner(View view){
        Spinner sortby = (Spinner)view.findViewById(R.id.sortby);

        sortby.setOnItemSelectedListener(new OnItemSelectedListener() {
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

    @Override
    public void onResume(){
        super.onResume();
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

    public void emptyDirectory() {
        File folder = context1.getFilesDir();
        File[] directoryListing = folder.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                child.delete();
            }
        } else {
            System.out.println("Empty or invalid directory");
        }
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

    public void deserializeList() {
        File folder = context1.getFilesDir();
        File[] directoryListing = folder.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String file_name = child.getName();
                if (file_name.contains("book-")) {
                    //System.out.println(file_name);
                    try {
                        Book b = jsonDeserialize(file_name, context1);
                        System.out.println(b.getTitle());
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        } else {
            System.out.println("Empty or invalid directory");
        }
    }

    /**
     * Sets up listview with booklist.
     * @param view
     * @param books Arraylist of books
     */
    private void setUpList(View view, List<Book> books){
        emptyDirectory();
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
        user.getUpdates().add(new Update(books.get(0), "New chapter", "Chapter 1 has been translated!"));
        user.getUpdates().add(new Update(books.get(0), "New chapter", "Chapter 1 has been translated!"));

        DatabaseHelper db = new DatabaseHelper(context1);
        db.wipe();

        for (Book book : books) {
            book.toJson(context1);
            db.createBook(book);
        }
        Book book = db.getAllBooks().get(0);
        book.getChapters().add(new Chapter("Chapter 3", "This is chapter 3", book.getId()));
        db.updateBook(book);

        for (Book b : db.getAllBooks()) {
            for (Chapter c : b.getChapters()) {
                System.out.println("Book ID: " + b.getId() + " " + b.getTitle() + ": " + c.getTitle());
            }
        }
        Log.d("Book Count", "Book Count: " + db.getAllBooks().size());
        Log.d("Chapter Count", "Chapter Count: " + db.getAllChapters().size());
        Log.d("Genre Count", "Genre Count: " + db.getAllGenres().size());

        //db.closeDB();

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
                myIntent.putExtra("index", position);
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