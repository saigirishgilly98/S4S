//Sharmila Biswas, Vikas B N(Notification)

package com.example.android.s4s;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.android.s4s.MainActivity.CHANNELid1;

public class addtocart extends AppCompatActivity {
    Button add;

    Button buy;
    AlertDialog.Builder builder;
    NotificationManagerCompat managerCompat;

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


                                openPayment(findViewById(R.id.button2));
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

    public void openPayment(View view)
    {
        Intent i = new Intent(addtocart.this, payment.class);
        startActivity(i);
    }

    public void openWishlist(View view)
    {
        Intent i = new Intent(addtocart.this, Wishlist.class);
        startActivity(i);
    }
    public void openMaps(View view)
    {
        Intent i = new Intent(addtocart.this, maps.class);
        startActivity(i);
    }

}

//Sharmila Biswas, Vikas B N(Notification)