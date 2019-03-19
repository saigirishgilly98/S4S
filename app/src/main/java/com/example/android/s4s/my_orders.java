package com.example.android.s4s;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class my_orders extends AppCompatActivity {

    Button buy, wish;


    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buy = (Button) findViewById(R.id.button1);
        wish = (Button) findViewById(R.id.button11);
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

    public void openPayment(View view) {
        Intent i = new Intent(this, payment.class);
        startActivity(i);
    }

    public void openPayment1(View view) {
        Intent i = new Intent(this, Wishlist.class);
        startActivity(i);

    }

    public void openMyOrders(View view) {
        Intent i = new Intent(this, CancelOrder.class);
        startActivity(i);
    }
}