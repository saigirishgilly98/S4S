//Sai Girish, Vikas B N(Notification)
package com.example.android.s4s;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static com.example.android.s4s.notificationfinal.CHANNELid1;


public class my_orders extends AppCompatActivity {

    Button buy, wish;
    NotificationManagerCompat manager;


    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = NotificationManagerCompat.from(this);

        buy = findViewById(R.id.button1);
        wish = findViewById(R.id.button11);
        builder = new AlertDialog.Builder(this);
        //this code is for backbutton
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayment(findViewById(R.id.button1));
            }
        });

        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayment1(findViewById(R.id.button11));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void sendmovenote() {
        Intent activityIntent = new Intent(getApplicationContext(), Wishlist.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, activityIntent, 0);
        Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.bpicon);
        @SuppressWarnings("deprecations")
        Notification builder = new NotificationCompat.Builder(getApplicationContext(), CHANNELid1)
                .setSmallIcon(R.drawable.bookstoreicon)
                .setLargeIcon(largeicon)
                .setContentTitle("Order moved to WishList")
                .setContentText("Your order has been removed")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("The book has been moved to your WishList.")
                        .addLine("Click to see your WishList"))
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        manager.notify(null, 3, builder);
    }


    public void openPayment(View view) {
        Intent i = new Intent(this, payment.class);
        startActivity(i);
    }

    public void openPayment1(View view) {
        Intent i = new Intent(this, Wishlist.class);
        sendmovenote();
        startActivity(i);

    }

    public void openMyOrders(View view) {
        Intent i = new Intent(this, CancelOrder.class);
        startActivity(i);
    }
}
//Sai Girish, Vikas B N(Notification)