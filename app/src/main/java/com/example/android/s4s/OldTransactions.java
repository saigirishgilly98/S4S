//Vikas B N
package com.example.android.s4s;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class OldTransactions extends AppCompatActivity {
    Button sales;
    Button purchases;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oldtransactions);
        sales = findViewById(R.id.saleslistbtn);
        purchases = findViewById(R.id.purchaseslistbtn);
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSalesList(findViewById(R.id.saleslistbtn));
            }
        });
        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPurchasesList(findViewById(R.id.purchaseslistbtn));
            }
        });
    }

    public void openSalesList(View view) {
        Intent i = new Intent(OldTransactions.this, OldTransactionsSales.class);
        startActivity(i);
    }

    public void openPurchasesList(View view) {
        Intent i = new Intent(OldTransactions.this, OldTransactionsPurchases.class);
        startActivity(i);
    }
}
//Vikas B N
