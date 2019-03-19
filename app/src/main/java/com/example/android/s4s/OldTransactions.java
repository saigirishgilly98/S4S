package com.example.android.s4s;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OldTransactions extends AppCompatActivity {


    private ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oldtransactions);


        BookAdapter adapter = new BookAdapter(this, books);

        ListView listView = (ListView) findViewById(R.id.list);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(OldTransactions.this, addtocart.class);
                startActivity(intent);
            }
        });


        DatabaseReference rootref, userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        try {
                            String author = ds1.child("AuthorsName").getValue().toString();

                            String title = ds1.child("BookName").getValue().toString();
                            String price = ds1.child("Price").getValue().toString();

                            books.add(new Book(title, author, price, "Add", R.drawable.ic_menu_gallery, R.drawable.book_ratings, R.drawable.ic_add_shopping_cart));

                        } catch (NullPointerException abcd) {

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
