//Sharmila Biswas, Vikas B N(Notification)

package com.example.android.s4s;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.android.s4s.MainActivity.CHANNELid1;
import static java.lang.Float.parseFloat;

public class addtocart extends AppCompatActivity {
    Button add;

    Button buy;
    AlertDialog.Builder builder;
    NotificationManagerCompat managerCompat;
    TextView desc;
    TextView address;
    RatingBar rating_bar;
    ImageView img;


    private FirebaseDatabase database;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        add = findViewById(R.id.button);
        buy = findViewById(R.id.button2);
        builder = new AlertDialog.Builder(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        managerCompat = NotificationManagerCompat.from(this);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        desc = findViewById(R.id.textView);
        address = findViewById(R.id.textView2);
        rating_bar = findViewById(R.id.ratingBar);
        img = findViewById(R.id.imageView1);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        final String bookid = bundle.getString("bookid");

        int index = 0;

        DatabaseReference rootref, userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            try {
                                String author = ds.child(bookid).child("AuthorsName").getValue().toString();
                                String title = ds.child(bookid).child("BookName").getValue().toString();
                                String price = ds.child(bookid).child("Price").getValue().toString();
                                String book_id = ds.child(bookid).getKey();
                                String edition = ds.child(bookid).child("BookEdition").getValue().toString();
                                String society = ds.child(bookid).child("Society").getValue().toString();
                                String district = ds.child(bookid).child("District").getValue().toString();
                                String pincode = ds.child(bookid).child("Pincode").getValue().toString();
                                String state = ds.child(bookid).child("State").getValue().toString();
                                String rating = ds.child(bookid).child("Rating").getValue().toString();
                                String seller_id = ds.getKey();
                                desc.setText("Book Name:" + title + "\nAuthor:" + author + "\nEdition:" + edition + "\nPrice:" + price + "\n");
                                address.setText("Address:" + society + "\n" + district + "\n" + pincode + "\n" + state);
                                rating_bar.setRating(parseFloat(rating));
                                //img.setImageURI(android.net.Uri.parse("gs://s4sproject.appspot.com/images"+book_id+".jpeg"));
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ book_id);
                                GlideApp.with(addtocart.this)
                                        .load(storageReference)
                                        .into(img);
                            } catch (NullPointerException abcd) {

                            }
                        }

                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("add to cart").setTitle("add to cart");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want this book to be added to Wishlist??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                //Toast.makeText(getApplicationContext(), "Successfully added to Wishlist!!",
                                //Toast.LENGTH_SHORT).show();
                                Sendwishnot();
                                openWishlist(findViewById(R.id.button1));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Not added to Wishlist!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Confirm");
                alert.show();


            }


        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("buy now").setTitle("buy now");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want buy this to book now? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                openMyOrders(findViewById(R.id.button2));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Order not placed!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Cancel");
                alert.show();
            }
        });
    }

    public void Sendwishnot() {
        Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, activityIntent, 0);
        String editTextTitle = "Addition to wishlist";
        String editTextMessage = "The book was successfully added to the wishlist";
        Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.bpicon);
        @SuppressWarnings("deprecation")
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNELid1)
                .setSmallIcon(R.drawable.bookstoreicon)
                .setLargeIcon(largeicon)
                .setContentTitle(editTextTitle)
                .setContentText(editTextMessage)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        managerCompat.notify(1, notification);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void openMyOrders(View view)
    {
        Bundle bundle = getIntent().getExtras();
        final String bookid = bundle.getString("bookid");
        database.getReference("Seller").child(mAuth.getCurrentUser().getUid()).child(bookid).child("Flag").setValue("1");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getUid());


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String latitudeA,longitudeA;
                latitudeA = dataSnapshot.child("latitude").getValue().toString();
                longitudeA = dataSnapshot.child("longitude").getValue().toString();
                System.out.println("Latitude A = "+latitudeA);
                Intent i = new Intent(addtocart.this, ShowStudentDetails.class);
                i.putExtra("bookid",bookid);
                i.putExtra("latitude",latitudeA);
                i.putExtra("longitude",longitudeA);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openWishlist(View view)
    {
        Bundle bundle = getIntent().getExtras();
        final String bookid = bundle.getString("bookid");
        database.getReference("Seller").child(mAuth.getCurrentUser().getUid()).child(bookid).child("Flag").setValue("3");
        Intent i = new Intent(addtocart.this, ShowWishlistDetails.class);
        i.putExtra("bookid",bookid);
        startActivity(i);
    }
    public void openMaps(View view)
    {
        Intent i = new Intent(addtocart.this, maps.class);
        startActivity(i);
    }

}

//Sharmila Biswas, Vikas B N(Notification)