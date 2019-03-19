package com.example.android.s4s;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CV extends Fragment {

    private RecyclerAdapter listAdapter;
    private ArrayList<Book> books4 = new ArrayList<>();
    private RecyclerView recycler4;


    public CV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        recycler4 = v.findViewById(R.id.recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler4.setLayoutManager(layoutManager);

        perform(v);

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void perform(View v) {


        listAdapter = new RecyclerAdapter(getContext(), books4);

        recycler4.setAdapter(listAdapter);


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

                            books4.add(new Book(title, author, price, "Add", R.drawable.ic_menu_gallery, R.drawable.book_ratings, R.drawable.ic_add_shopping_cart));

                        } catch (NullPointerException abcd) {

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listAdapter.notifyDataSetChanged();

        /*

        ArrayList<Book> books = new ArrayList<Book>();


        BookAdapter adapter = new BookAdapter(getActivity(), books);

         ListView listView = (ListView) v.findViewById(R.id.list);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(),addtocart.class);
                startActivity(intent);
            }
        });*/


    }


}
