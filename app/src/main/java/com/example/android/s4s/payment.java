//Shishirdeep, Vikas B N(Notification)
package com.example.android.s4s;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;

import static com.example.android.s4s.notificationfinal.CHANNELid1;
import static java.lang.Float.parseFloat;


public class payment extends AppCompatActivity {
    Button confirm;
    Button cancel;
    TextView totalPrice,bookName,author,price,wallet,comission,remainingBalance;
    ImageView img;
    String price2;
    double totalPrice2;
    AlertDialog.Builder builder;
    private Spinner spinner1;
    NotificationManagerCompat manager;
    private static DecimalFormat df = new DecimalFormat("0.00");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = NotificationManagerCompat.from(this);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        final String bookid = bundle.getString("bookid");


        confirm = findViewById(R.id.button);
        cancel = findViewById(R.id.button1);
        totalPrice = findViewById(R.id.textView5);
        img = findViewById(R.id.imageView2);
        bookName = findViewById(R.id.textView);
        author = findViewById(R.id.textView2);
        price = findViewById(R.id.textView3);
        wallet = findViewById(R.id.textView4);
        comission = findViewById(R.id.textView7);
        remainingBalance = findViewById(R.id.textView8);


        DatabaseReference rootref, userref;
        rootref = FirebaseDatabase.getInstance().getReference();
        userref = rootref.child("Seller");
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        String author2 = ds.child(bookid).child("AuthorsName").getValue().toString();
                        String title = ds.child(bookid).child("BookName").getValue().toString();
                        price2 = ds.child(bookid).child("Price").getValue().toString();
                        String book_id = ds.child(bookid).getKey();
                        bookName.setText("Title: "+title);
                        author.setText("Author: "+author2);
                        price.setText("Price: "+price2);
                        double comission2 = (0.02 * Integer.parseInt(price2));
                        totalPrice2 = Integer.parseInt(price2) + (0.02 * Integer.parseInt(price2));
                        comission.setText("Comission: "+ df.format(comission2));
                        totalPrice.setText("Total Price of Book: "+df.format(totalPrice2));
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ book_id);
                        GlideApp.with(payment.this)
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


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

        Query query = reference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        // do something with the individual "ds"
                        String wallet2 = ds.child("wallet").getValue().toString();
                        wallet.setText("Current Balance in Wallet: "+wallet2);
                        double remainingBalance2 = Double.parseDouble(wallet2) - totalPrice2;
                        remainingBalance.setText("Remaining Balance(If Bought): "+df.format(remainingBalance2));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        builder = new AlertDialog.Builder(this);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message1).setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to confirm?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                paymentnote();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "payment not confirmed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Confirm");
                alert.show();


            }


        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message2).setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to cancel?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(), "payment cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "payment not cancelled",
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

    public void paymentnote() {
        Intent activityIntent = new Intent(getApplicationContext(), Profile1.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, activityIntent, 0);
        Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.bpicon);
        @SuppressWarnings("deprecations")
        Notification builder = new NotificationCompat.Builder(getApplicationContext(), CHANNELid1)
                .setSmallIcon(R.drawable.bookstoreicon)
                .setLargeIcon(largeicon)
                .setContentTitle("Change in wallet balance")
                .setContentText("Your wallet has been updated")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Your payment was successfully complete.")
                        .addLine("Click to see updated wallet balance"))
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        manager.notify(null, 2, builder);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
//Shishirdeep, Vikas B N(Notification)

