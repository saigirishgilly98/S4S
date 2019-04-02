//Rahul Gite
package com.example.android.s4s;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IT extends Fragment {

    BookAdapter adapter;
    // private RecyclerAdapter listAdapter;
    private ArrayList<Book> books3 = new ArrayList<>();
    // private RecyclerView recycler3;


    public IT() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cs, container, false);
        books3.clear();
        perform(v);

        return v;
    }

    @Override
    public void onDestroyView() {
        adapter.clear();
        super.onDestroyView();
    }

    public void perform(View v) {


        adapter = new BookAdapter(getActivity(), books3);

        ListView listView = v.findViewById(R.id.list);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), addtocart.class);
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
                            String branch = ds1.child("BRANCH").getValue().toString();
                            if (branch.compareToIgnoreCase("INFORMATION TECHNOLOGY") == 0) {
                                String author = ds1.child("AuthorsName").getValue().toString();
                                String title = ds1.child("BookName").getValue().toString();
                                String price = ds1.child("Price").getValue().toString();
                                String book_id = ds1.getKey();
                                String seller_id = ds.getKey();

                                books3.add(new Book(branch, title, author, price, book_id, seller_id));
                                adapter.notifyDataSetChanged();
                            }

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

    @Override
    public void onStop() {
        super.onStop();
    }

}
//Rahul Gite
