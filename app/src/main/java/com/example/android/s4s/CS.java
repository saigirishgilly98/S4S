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
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class CS extends Fragment {

    BookAdapter adapter;


    public CS() {
        // Required empty public constructor
    }

    // private RecyclerAdapter listAdapter;
    private ArrayList<Book> books = new ArrayList<>();
    //private RecyclerView recycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_cs, container, false);
        books.clear();
        perform(v);
        return v;
    }

    @Override
    public void onDestroyView() {
        adapter.clear();
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void perform(View v) {


        adapter = new BookAdapter(getActivity(), books);

        final ListView listView = v.findViewById(R.id.list);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // TableRow tableRow = (TableRow) (listView.getItemAtPosition(position));
                //String bookid = ((TextView) tableRow.findViewById(R.id.book_title)).getText().toString();
                //String bookid = listView.getItemAtPosition(position).toString();
                String bookid = ((TextView) view.findViewById(R.id.book_id)).getText().toString();
                Intent intent = new Intent(getContext(), addtocart.class);
                intent.putExtra("bookid",bookid);
                startActivity(intent);
            }
        });


        DatabaseReference rootref, userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {

                        try {
                            String branch = ds1.child("BRANCH").getValue().toString();
                            if (branch.compareToIgnoreCase("COMPUTER SCIENCE") == 0) {
                                String author = ds1.child("AuthorsName").getValue().toString();
                                String title = ds1.child("BookName").getValue().toString();
                                String price = ds1.child("Price").getValue().toString();
                                String book_id = ds1.getKey();
                                String seller_id = ds.getKey();

                                books.add(new Book(branch, title, author, price, book_id, seller_id));
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
}

//Rahul Gite