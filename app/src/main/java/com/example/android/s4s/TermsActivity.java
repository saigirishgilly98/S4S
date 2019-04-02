//Vikas B N
package com.example.android.s4s;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnc);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    public void openLogin(View view) {
        CheckBox cb = findViewById(R.id.checkBox);
        if(cb.isChecked()) {
            Toast.makeText(getApplicationContext(), "Thanks for accepting the terms",
                    Toast.LENGTH_SHORT).show();

            Intent i = new Intent(TermsActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Please accept the terms to proceed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}

//Vikas B N
