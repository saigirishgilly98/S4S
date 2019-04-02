//Vasudev B M
package com.example.android.s4s;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileAdmin extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    StorageReference mStorage;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String uid;
    SharedPreferences sp1;
    String url;
    TextView balance, ads;

    CircleImageView imageView1;

    TextView profileName, profileEmail, profilePhone, profileUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        DatabaseReference rootRef, userRef;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        imageView1 = findViewById(R.id.profile_pic1);

        //imageView1.setVisibility(View.VISIBLE);
        //  Picasso.with(Profile1.this).load("https://image.flaticon.com/icons/svg/236/236831.svg").fit().centerCrop().into(imageView1);


        //noinspection ConstantConditions
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("url").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //noinspection ConstantConditions
                url = dataSnapshot.getValue().toString();
                Picasso.with(getApplicationContext()).load(url).fit().centerCrop().into(imageView1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        //mStorage = FirebaseStorage.getInstance().getReference("Profile Pics");
        userRef = rootRef.child("User").child(mAuth.getUid());


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String phone = dataSnapshot.child("phone").getValue(String.class);
                String bal = dataSnapshot.child("balance").getValue(String.class);
                String num = dataSnapshot.child("number").getValue(String.class);


                String imageurl = dataSnapshot.child("url").getValue().toString();
                if (imageurl != " ")
                    Picasso.with(ProfileAdmin.this).load(imageurl).fit().centerCrop().into(imageView1);


                profileName = findViewById(R.id.profile_name1);
                profileEmail = findViewById(R.id.profile_email1);
                profilePhone = findViewById(R.id.profile_phone1);
                balance = findViewById(R.id.profile_balance1);
                profileUsers = findViewById(R.id.profile_no_of_users);


                profileName.setText(name);
                profileEmail.setText(email);
                profilePhone.setText(phone);
                balance.setText(bal);
                profileUsers.setText(num);

                // Picasso.with(Profile1.this).load(mAuth.getUid()).into(imageView1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }


    public void onBackPressed() {
        Intent i = new Intent(ProfileAdmin.this, AdminActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void openProfileEdit1(View v) {
        Intent i = new Intent(ProfileAdmin.this, ProfileEditAdmin.class);
        startActivity(i);
    }

}

//Vasudev B M








