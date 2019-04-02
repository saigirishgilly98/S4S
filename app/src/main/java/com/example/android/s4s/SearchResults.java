//Rahul Gite
package com.example.android.s4s;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    Book book_temp;
    // private RecyclerView recycler;
    BookAdapter adapter;
    ListView searchlist;
    int option;
    private EditText editText;
    private ImageButton search;
    //private RecyclerAdapter listAdapter;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = findViewById(R.id.search);
        search = findViewById(R.id.search_button);
        books = new ArrayList<>();

        adapter = new BookAdapter(SearchResults.this, books);

        searchlist = findViewById(R.id.searchlist);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                UserSearch(query, 4);
            }
        });

        Spinner dropdown = findViewById(R.id.spinner1);

        List<String> filter = new ArrayList<>();
        filter.add(0, "FILTER");
        filter.add("PRICE");
        filter.add("DISTANCE");
        filter.add("RATINGS");
        ArrayAdapter<String> filteradapter = new ArrayAdapter(SearchResults.this, android.R.layout.simple_spinner_item, filter);

        filteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setAdapter(filteradapter);

        final DatabaseReference rootref, userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");

        //This was for Filters, but it is not working

        // BookAdapter bookAdapter = new BookAdapter(SearchResults.this, books);

        //ListView listView = (ListView)findViewById(R.id.list);

        //listView.setAdapter(bookAdapter);

        /*dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 1: String query = editText.getText().toString();
                         if(query!=null)
                         {
                             UserSearch(query,1);
                         }
                        break;

                    case 2: String query1 = editText.getText().toString();
                       if(query1!=null)
                       {
                           UserSearch(query1,2);
                       }
                       break;

                    case 3: String query2 = editText.getText().toString();
                         if(query2!=null)
                         {
                             UserSearch(query2,3);
                         }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });*/


    }


    private void UserSearch(String query, int option1) {
        DatabaseReference rootref, userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");

        final String q = query;
        option = option1;

        searchlist.setAdapter(adapter);


        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String q1 = editText.getText().toString();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {

                        //This was for Filters, but it is not working

                       /* if(option==1)
                        {
                            try {
                                String name = ds1.child("AuthorsName").getValue().toString();
                                if (name.compareToIgnoreCase(q1) == 0) {
                                    String branch = ds1.child("BRANCH").getValue().toString();
                                    String author = ds1.child("AuthorsName").getValue().toString();
                                    String title = ds1.child("BookName").getValue().toString();
                                    String price = ds1.child("Price").getValue().toString();
                                    String book_id = ds1.getKey().toString();
                                    String seller_id = ds.getKey().toString();

                                    books.add(new Book(branch, title, author, price, book_id, seller_id));
                                    adapter.notifyDataSetChanged();

                                }

                            } catch (NullPointerException abcd) {

                                Toast.makeText(SearchResults.this, "NullPointer1", Toast.LENGTH_SHORT).show();

                            }

                            Collections.sort(books, new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    if(Integer.parseInt(o1.getmPrice())<=Integer.parseInt(o2.getmPrice()))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return -1;
                                    }
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }

                        else if(option==2)
                        {
                            try {
                                String name = ds1.child("AuthorsName").getValue().toString();
                                if (name.compareToIgnoreCase(q1) == 0) {
                                    String branch = ds1.child("BRANCH").getValue().toString();
                                    String author = ds1.child("AuthorsName").getValue().toString();
                                    String title = ds1.child("BookName").getValue().toString();
                                    String price = ds1.child("Price").getValue().toString();
                                    String book_id = ds1.getKey().toString();
                                    String seller_id = ds.getKey().toString();

                                    books.add(new Book(branch, title, author, price, book_id, seller_id));
                                    adapter.notifyDataSetChanged();
                                }

                            } catch (NullPointerException abcd) {

                                Toast.makeText(SearchResults.this, "NullPointer2", Toast.LENGTH_SHORT).show();

                            }
                            Collections.sort(books, new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    if(Integer.parseInt(o1.getmPrice())<=Integer.parseInt(o2.getmPrice()))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return -1;
                                    }
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }

                        else if(option==3)
                        {
                            try {
                                String name = ds1.child("AuthorsName").getValue().toString();
                                if (name.compareToIgnoreCase(q1) == 0) {
                                    String branch = ds1.child("BRANCH").getValue().toString();
                                    String author = ds1.child("AuthorsName").getValue().toString();
                                    String title = ds1.child("BookName").getValue().toString();
                                    String price = ds1.child("Price").getValue().toString();
                                    String book_id = ds1.getKey().toString();
                                    String seller_id = ds.getKey().toString();

                                    books.add(new Book(branch, title, author, price, book_id, seller_id));
                                    adapter.notifyDataSetChanged();
                                }

                            } catch (NullPointerException abcd) {

                                Toast.makeText(SearchResults.this, "NullPointer3", Toast.LENGTH_SHORT).show();


                            }
                            Collections.sort(books, new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    if(Integer.parseInt(o1.getmPrice())<=Integer.parseInt(o2.getmPrice()))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return -1;
                                    }
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }


                        else */
                        if (option == 4) {
                            try {
                                String name = ds1.child("AuthorsName").getValue().toString();
                                String tt = ds1.child("AuthorsName").getValue().toString();
                                if (name.compareToIgnoreCase(q1) == 0 || tt.compareToIgnoreCase(q1) == 0) {
                                    String branch = ds1.child("BRANCH").getValue().toString();
                                    String author = ds1.child("AuthorsName").getValue().toString();
                                    String title = ds1.child("BookName").getValue().toString();
                                    String price = ds1.child("Price").getValue().toString();
                                    String book_id = ds1.getKey();
                                    String seller_id = ds.getKey();

                                    books.add(new Book(branch, title, author, price, book_id, seller_id));
                                    adapter.notifyDataSetChanged();
                                }

                            } catch (NullPointerException abcd) {

                                Toast.makeText(SearchResults.this, "NullPointer4", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}

//Rahul Gite
