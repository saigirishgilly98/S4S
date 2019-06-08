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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile1 extends AppCompatActivity {
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

    TextView profileName, profileLocality, profileCity, profileDistrict, profileState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseReference rootRef, userRef;
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        imageView1 = findViewById(R.id.profile_pic);

        //imageView1.setVisibility(View.VISIBLE);
        //  Picasso.with(Profile1.this).load("https://image.flaticon.com/icons/svg/236/236831.svg").fit().centerCrop().into(imageView1);


        //noinspection ConstantConditions
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("url").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //noinspection ConstantConditions
                /**url = dataSnapshot.getValue().toString();
                Picasso.with(getApplicationContext()).load(url).fit().centerCrop().into(imageView1);**/
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profilepic/"+ mAuth.getCurrentUser().getUid());
                GlideApp.with(getApplicationContext())
                        .load(storageReference)
                        .into(imageView1);
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
        userRef = rootRef.child("User").child(mAuth.getCurrentUser().getUid());


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);
                String locality = dataSnapshot.child("locality").getValue(String.class);
                String city = dataSnapshot.child("city").getValue(String.class);
                String district = dataSnapshot.child("district").getValue(String.class);
                String state = dataSnapshot.child("state").getValue(String.class);
                String bal = dataSnapshot.child("wallet").getValue(String.class);
                // int  ad = (int)dataSnapshot.child("ads").getValue();

                try {
                    /**String imageurl = dataSnapshot.child("url").getValue().toString();
                    if (imageurl != " ")
                        Picasso.with(getApplicationContext()).load(imageurl).fit().centerCrop().into(imageView1);**/
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profilepic/"+ mAuth.getCurrentUser().getUid());
                    GlideApp.with(getApplicationContext())
                            .load(storageReference)
                            .into(imageView1);
                        
                }catch (Exception e){}

                profileName = findViewById(R.id.profile_name);
                profileLocality = findViewById(R.id.profile_locality);
                profileCity = findViewById(R.id.profile_city);
                profileDistrict = findViewById(R.id.profile_district);
                profileState = findViewById(R.id.profile_state);
                balance = findViewById(R.id.profile_balance);
                // ads = findViewById(R.id.profile_no_of_ads);


                profileName.setText(name);
                profileLocality.setText(locality);
                profileCity.setText(city);
                profileDistrict.setText(district);
                profileState.setText(state);
                balance.setText(bal);
                // ads.setText(ad);

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
        Intent i = new Intent(Profile1.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void openProfileEdit1(View v) {
        Intent i = new Intent(Profile1.this, ProfileEdit1.class);
        startActivity(i);
    }

}

//Vasudev B M








