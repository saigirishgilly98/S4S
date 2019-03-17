package com.example.android.s4s;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEdit1 extends MainActivity {

    DatabaseReference databaseReference;
    EditText email_edit, phone_edit, name_edit;
    String phone, email, name;

    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        name_edit = (EditText) findViewById(R.id.editname);
        phone_edit = (EditText) findViewById(R.id.editphone);
        email_edit = (EditText) findViewById(R.id.editmail);

        email = email_edit.getText().toString();
        phone = phone_edit.getText().toString();
        name = name_edit.getText().toString();


        databaseReference = FirebaseDatabase.getInstance().getReference();

        ImageButton ib = (ImageButton) findViewById(R.id.tick);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            @NonNull
            public void onClick(View v) {

                final DatabaseReference rootRef, userRef;

                rootRef = FirebaseDatabase.getInstance().getReference();
                userRef = rootRef.child("User").child((mAuth.getCurrentUser()).getUid());


                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        try {
                            userRef.child("User").child(mAuth.getUid()).child("name").setValue(name);
                            userRef.child("User").child(mAuth.getUid()).child("phone").setValue(phone);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
    public void onBackPressed(){
        Intent i = new Intent(this, Profile1.class);
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
