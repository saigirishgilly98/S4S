package com.example.android.s4s;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CS extends Fragment {


    public CS() {
        // Required empty public constructor
    }

    private RecyclerAdapter listAdapter;
    private ArrayList<Book> books = new ArrayList<>();
    private RecyclerView recycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // View v = inflater.inflate(R.layout.fragment_cs, container, false);

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);





        perform(v);


        return v;
    }



    @Override
    public void onStop() {
        super.onStop();
    }

    public void perform(View v) {


        recycler  =  v.findViewById(R.id.recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

        listAdapter = new RecyclerAdapter(getContext(),books);

        recycler.setAdapter(listAdapter);


        DatabaseReference rootref,userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");

//        userref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
////                    Log.d("fuck",ds.toString());
//                    String author = ds.child("Author's Name").getValue().toString();
//                    Log.d("fuck",author.toString());
//                    String title  = ds.child("Book Name").getValue().toString();
//                    String price = ds.child("Price").getValue().toString();
//
//                    books.add(new Book(title,author,price,"Add",R.drawable.ic_menu_gallery,R.drawable.book_ratings,R.drawable.ic_add_shopping_cart));
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




        /*for(int i = 0;i<10;i++)
        {
            books.add(new Book("The Lost Symbol","Dan Brown", "Rs.500", "Add",R.drawable.ic_menu_gallery,R.drawable.book_ratings,R.drawable.ic_add_shopping_cart));
        }*/

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


