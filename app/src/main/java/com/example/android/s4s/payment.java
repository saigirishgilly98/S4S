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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.android.s4s.notificationfinal.CHANNELid1;


public class payment extends AppCompatActivity {
    Button confirm;
    Button cancel;
    AlertDialog.Builder builder;
    private Spinner spinner1;
    NotificationManagerCompat manager;


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


        confirm = findViewById(R.id.button);
        cancel = findViewById(R.id.button1);
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

