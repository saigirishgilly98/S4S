//Veena Nagar
package com.example.android.s4s;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Pop extends Activity {
    Button button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)( height*.2));

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
                Toast.makeText(getApplicationContext(), "Not uploaded for sale!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
                Toast.makeText(getApplicationContext(), "successfully uploaded for sale!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
//Veena Nagar