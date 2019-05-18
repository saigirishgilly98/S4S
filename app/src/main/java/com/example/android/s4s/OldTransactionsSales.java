//Sai Girish
package com.example.android.s4s;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OldTransactionsSales extends AppCompatActivity {


    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<SalesDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_transactions_sales);

        recyclerView = findViewById(R.id.recyclerView2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(OldTransactionsSales.this));

        progressDialog = new ProgressDialog(OldTransactionsSales.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();


        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Seller").child(currentFirebaseUser.getUid());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference messageRef = database.getReference("Seller");
        Query queryRef = messageRef.child(currentFirebaseUser.getUid())
                .orderByChild("Flag")
                .equalTo("0");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    SalesDetails salesDetails = dataSnapshot.getValue(SalesDetails.class);
                    list.add(salesDetails);

                }

                adapter = new RecyclerViewAdapterSales(OldTransactionsSales.this, list);


                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /** databaseReference.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(DataSnapshot snapshot) {

        //DataSnapshot childSnapshot = snapshot.child(currentFirebaseUser.getUid());
        //Iterable<DataSnapshot> childChildren = childSnapshot.getChildren();

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

        StudentDetails studentDetails = dataSnapshot.getValue(StudentDetails.class);
        list.add(studentDetails);

        }

        adapter = new RecyclerViewAdapter(ShowStudentDetails.this, list);

        recyclerView.setAdapter(adapter);

        progressDialog.dismiss();
        }

        @Override public void onCancelled(DatabaseError databaseError) {

        progressDialog.dismiss();

        }
        });**/

    }
}

//Sai Girish
