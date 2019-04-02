//Vasudev B M(Major), Rahul Gite(Minor), Vikas B N(Notification)

package com.example.android.s4s;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sp, sp1, sp2;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CircleImageView imageView;
    TextView t1, t2;
    DatabaseReference rootRef, userRef;
    private Boolean exit = false;
    public static final String CHANNELid1 = "Channel 1";
    public static final String CHANNELid2 = "Channel 2";
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannels();


        sp2 = getSharedPreferences("uid", MODE_PRIVATE);
        sp2.getString("getuid", null);

        //SharedPreferences for Logout
        sp = getSharedPreferences("login",
                MODE_PRIVATE);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        sp1 = getSharedPreferences("uid",
                MODE_PRIVATE);
        sp1.edit().putString("uid", mAuth.getUid()).apply();
        String id = sp1.getString("getuid", null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager Pager = findViewById(R.id.viewpager);

        tabpagerAdapter Tabpageradapter = new tabpagerAdapter(getSupportFragmentManager(), MainActivity.this);
        Pager.setAdapter(Tabpageradapter);
        tabLayout.setupWithViewPager(Pager);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        imageView = findViewById(R.id.imageView);

        final ImageView searchbutton = findViewById(R.id.hts);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchResults.class);
                startActivity(i);
            }
        });

        if (currentUser == null)
            Toast.makeText(this, "Null User Facebook", Toast.LENGTH_SHORT).show();
        else {
            FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("url").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //noinspection ConstantConditions
                    String url = dataSnapshot.getValue().toString();
                    Picasso.with(getApplicationContext()).load(url).fit().centerCrop().into(imageView);
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
                    imageView = findViewById(R.id.imageView);


                    String imageurl = dataSnapshot.child("url").getValue().toString();
                    if (imageurl != " ")
                        Picasso.with(getApplicationContext()).load(imageurl).fit().centerCrop().into(imageView);

                    t1 = findViewById(R.id.nav_head_name);
                    t2 = findViewById(R.id.textView);

                    t1.setText(name);
                    t2.setText(email);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }


    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNELid1,
                    "First Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("The first notification channel");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNELid2,
                    "Second Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.setDescription("The second notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) { //different pages
        switch (item.getItemId()) {
            case R.id.old_transactions:
                Intent intent0 = new Intent(this, OldTransactions.class);
                startActivity(intent0);
                break;

            case R.id.my_orders:
                Intent intent3 = new Intent(this, ShowStudentDetails.class);
                startActivity(intent3);
                break;
            case R.id.tnc:
                Intent intent1 = new Intent(this, TermsActivity.class);
                startActivity(intent1);
                break;

            case R.id.wishlist:
                Intent intent6 = new Intent(this, Wishlist.class);
                startActivity(intent6);
                break;
            case R.id.logout:
                mAuth.signOut();
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });

                Intent intent8 = new Intent(this, LoginActivity.class);
                startActivity(intent8);
                sp.edit().putBoolean("logged", false).apply();

                break;
            case R.id.feedback_form:
                Intent intent2 = new Intent(this, Feedback.class);
                startActivity(intent2);
                break;

            case R.id.seller:
                Intent intent5 = new Intent(this, Seller.class);
                startActivity(intent5);
                break;

            case R.id.buyer:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openProfile1(View view) {
        Intent intent = new Intent(MainActivity.this, Profile1.class);
        startActivity(intent);
    }

    public void openProfileEdit1(View view) {
        Intent i = new Intent(this, ProfileEdit1.class);
        startActivity(i);
    }

    public void openBuyer(View view) {

        String mess = "Updated Successfully";
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void openPayment(View view) {
        Intent i = new Intent(this, payment.class);
        startActivity(i);
    }
}


//Vasudev B M(Major), Rahul Gite(Minor), Vikas B N(Notification)


