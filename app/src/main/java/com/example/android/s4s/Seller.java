package com.example.android.s4s;

//Veena Nagar, Vikas B N(Notification)


import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.s4s.notificationfinal.CHANNELid1;


public class Seller extends AppCompatActivity {
    private static final String TAG = "FragmentActivity";
    int num;
    int ad = 0;
    NotificationManagerCompat manager;
    TextView ads;



    // Global variable
    Button  button;
    AlertDialog.Builder builder, builder3;
    Intent I;
    RatingBar rb;

    EditText editText, editText2, editText3, editText4, editText5, editText7, editText8, editText9,
            editText10, editText11;
    TextInputLayout name_layout, author_layout, phone1_layout, edition_layout, price_layout,
            publisher_layout, spinner_layout, rating_layout, locality_layout, district_layout,
            state_layout, pincode_layout, upload_layout, upload1_layout;
    private FirebaseDatabase database;
    private DatabaseReference book_name, book_author, book_edition, book_publisher, book_price,
            book_rating, sel_society, sel_district, sel_pincode, sel_phn, sel_state, flag,
            book_branch, book_key, user_key;

    private FirebaseDatabase databasebook;
    private FirebaseAuth mAuth;

    private static int index = 0;
    ImageView viewImage;
    private Button btnSelectPhoto;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    //   java class for adding profile photo
    private Uri selectedImage,selectedImage1;

    int PLACE_PICKER_REQUEST = 5;

//  to store image

    private FirebaseStorage mstorage;

    private StorageReference store;

    private DatabaseReference databaseReference;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference data;
    String item;

    public static final String FB_STORAGE_PATH = "image/";

    public static final String FB_DATABASE_PATH = "image";

    int i1, x, y = 0;
    int z = 0;
    String key;
    TextView tvplace;
    float rat = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder ();
        StrictMode.setVmPolicy (builder1.build ());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = NotificationManagerCompat.from(this);

        //int i1;

//       for variable creation
        builder = new AlertDialog.Builder(this);
        builder3 = new AlertDialog.Builder(this);
        button = findViewById(R.id.button);
        rb = findViewById(R.id.ratingBar2);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText7 = findViewById(R.id.editText7);
        editText8 = findViewById(R.id.editText8);
        editText9 = findViewById(R.id.editText9);
        editText10 = findViewById(R.id.editText10);
        editText11 = findViewById(R.id.editText11);
        spinner_layout = findViewById(R.id.layout_branch);
        name_layout= findViewById(R.id.layout_name);
        author_layout = findViewById(R.id.layout_author);
        phone1_layout = findViewById(R.id.layout_phone_no);
        publisher_layout = findViewById(R.id.layout_publisher);
        price_layout = findViewById(R.id.layout_price);
        edition_layout = findViewById(R.id.layout_edition);
        locality_layout = findViewById(R.id.layout_society);
        district_layout = findViewById(R.id.layout_district);
        state_layout = findViewById(R.id.layout_state);
        pincode_layout = findViewById(R.id.layout_pincode);
        upload_layout = findViewById(R.id.layout_upload1);
        upload1_layout = findViewById(R.id.layout_upload2);
        rating_layout = findViewById(R.id.layout_rating);
        ads = findViewById(R.id.profile_no_of_ads);
        database = FirebaseDatabase.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //tvplace=(TextView)findViewById(R.id.tvplace);


//        dropdown of branch
        final Spinner spinner = findViewById(R.id.spinner);
        List<String> branch = new ArrayList<>();
        branch.add(0, "CHOOSE BRANCH");
        branch.add("COMPUTER SCIENCE");
        branch.add("MECHANICAL");
        branch.add("ELECTRICAL");
        branch.add("ELECTRONICS");
        branch.add("INFORMATION TECHNOLOGY");
        branch.add("CIVIL");
        branch.add("MINING");
        branch.add("METALLURGY");


//        dropdown validation and working for getting branch
        ArrayAdapter<String> adapter = new ArrayAdapter(Seller.this, android.R.layout.simple_spinner_item, branch);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("CHOOSE BRANCH")) {
                    x = 0;

                } else {
                    item = parent.getItemAtPosition(position).toString();
                    x = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        int i = index;
        index++;

//        database creation
        databasebook=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Seller");
        key = databaseReference.push().getKey();
        book_branch = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("BRANCH");
        book_name = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("BookName");
        book_author = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("AuthorsName");
        book_edition = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("BookEdition");
        book_publisher = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Publisher");
        book_price = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Price");
        //book_rating = databasebook.getReference("Seller").child("Book");
        sel_society = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Society");
        sel_district = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("District");
        sel_pincode = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Pincode");
        sel_state = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("State");
        sel_phn = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("ContactDetails");
        flag = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Flag");
        book_rating = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Rating");
        book_key = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Book_Id");
        user_key = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("User_Id");



        //below content is for placing backbutton in the toolbar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //initialise firebase variables
        store = FirebaseStorage.getInstance().getReference();
        data = FirebaseDatabase.getInstance().getReference();


        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        imageView = findViewById(R.id.frontpic);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



        //get and set rating
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rat = rating;
            }
        });


        // below code is the validation code for the details
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                validation of each and every field

                if (editText.getText().toString().equals("")) {
                    name_layout.setError("Name of the book is req");

                }
                if (editText.getText().toString().equals(" ") || editText.getText().toString().equals("  ") || editText.getText().toString().equals("    ")) {
                    name_layout.setError("space is not accepted");

                } else name_layout.setError(null);
                if (editText2.getText().toString().equals("")) {
                    author_layout.setError("Author's Name is req");

                }
                if (editText2.getText().toString().equals(" ") || editText2.getText().toString().equals("  ") || editText2.getText().toString().equals("    ")) {
                    author_layout.setError("space is not accepted");

                } else author_layout.setError(null);
                if (editText3.getText().toString().equals("")) {
                    price_layout.setError("Expected price is req");

                }
                if (editText3.getText().toString().equals(" ") || editText3.getText().toString().equals("  ") || editText3.getText().toString().equals("    ")) {
                    price_layout.setError("space is not accepted");

                } else price_layout.setError(null);
                if (editText4.getText().toString().equals("")) {
                    edition_layout.setError("Edition is req");

                }
                if (editText4.getText().toString().equals(" ") || editText4.getText().toString().equals("  ") || editText4.getText().toString().equals("     ")) {
                    edition_layout.setError("space is not accepted");

                } else edition_layout.setError(null);
                if (editText5.getText().toString().equals("")) {
                    publisher_layout.setError("Publisher's name is req");

                }
                if (editText5.getText().toString().equals(" ") || editText5.getText().toString().equals("  ") || editText5.getText().toString().equals("    ")) {
                    publisher_layout.setError("space is not accepted");

                } else publisher_layout.setError(null);
                if (editText7.getText().toString().equals("")) {
                    locality_layout.setError("enter you apartment/society/village name is req");
                }
                if (editText7.getText().toString().equals(" ") || editText7.getText().toString().equals("  ") || editText7.getText().toString().equals("    ")) {
                    locality_layout.setError("space is not accepted");

                } else locality_layout.setError(null);
                if (editText8.getText().toString().equals("")) {
                    district_layout.setError("District name is req");

                }
                if (editText8.getText().toString().equals(" ") || editText8.getText().toString().equals("  ") || editText8.getText().toString().equals("    ")) {
                    district_layout.setError("space is not accepted");

                } else district_layout.setError(null);
                if (editText9.getText().toString().equals("")) {
                    pincode_layout.setError("Pincode is req");
                }
                else if (!isValidpincode(editText9)) {
                    pincode_layout.setError("Pincode is wrong");

                } else pincode_layout.setError(null);


                if (editText10.getText().toString().equals("")) {
                    state_layout.setError("State name is req");

                }
                if (editText10.getText().toString().equals(" ") || editText10.getText().toString().equals("  ") || editText10.getText().toString().equals("   ")) {
                    state_layout.setError("space is not accepted");

                } else state_layout.setError(null);
                if (editText11.getText().toString().equals("")) {
                    phone1_layout.setError("phone no. req");

                }
                else if (!isValidMobile(editText11)) {
                    phone1_layout.setError("phone no is wrong");

                } else phone1_layout.setError(null);

                if (!editText.getText().toString().equals("") && !editText2.getText().toString().equals("") &&
                        !editText3.getText().toString().equals("") && !editText4.getText().toString().equals("") &&
                        !editText5.getText().toString().equals("") && !editText7.getText().toString().equals("") &&
                        !editText8.getText().toString().equals("") && !editText9.getText().toString().equals("") &&
                        !editText10.getText().toString().equals("") && !editText11.getText().toString().equals("") &&
                        isValidMobile(editText11) && isValidpincode(editText9)) {

                    if (rb.getRating() == 0) {
                        Toast.makeText(getApplicationContext(), "please Rate the book based on it's condition!", Toast.LENGTH_SHORT).show();
                    } else if (x == 0) {
                        Toast.makeText(getApplicationContext(), "please choose the branch of the book!", Toast.LENGTH_SHORT).show();
                    } else if (y == 0) {
                        Toast.makeText(getApplicationContext(), "please add the front page of the book!", Toast.LENGTH_SHORT).show();
                    }

//                    popup
                    else {
                        builder.setMessage("Do you want to sell the book ??")
                                .setCancelable(false)

                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        DatabaseReference rootRef, userRef;
                                        rootRef = FirebaseDatabase.getInstance().getReference();
                                        //mStorage = FirebaseStorage.getInstance().getReference("Profile Pics");
                                        userRef = rootRef.child("User").child(mAuth.getUid());

                                        userRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                try {
                                                    ad = Integer.valueOf(dataSnapshot.child("ads").getValue().toString());

                                                    ad++;
                                                }catch (Exception e){}

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        openPayment(findViewById(R.id.button));

                                        addbook();
                                        ads = findViewById(R.id.profile_no_of_ads);
                                        database.getReference().child("User").child(mAuth.getUid()).child("ads").setValue(ad);

                                        // ads.setText(Integer.toString(ad));

                                        sendsellnote();
                                        finish();

//



                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                        Toast.makeText(getApplicationContext(), "Book is not added for sale!",
                                                Toast.LENGTH_SHORT).show();
                                        Intent i6 = new Intent(Seller.this, Seller.class);


                                    }
                                });
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Confirm");
                        alert.show();
                    }

                }


            }

        });

    }



    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        y = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void sendsellnote() {
        Intent activityIntent = new Intent(getApplicationContext(), Seller.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, activityIntent, 0);
        Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.bpicon);
        @SuppressWarnings("deprecations")
        Notification builder = new NotificationCompat.Builder(getApplicationContext(), CHANNELid1)
                .setSmallIcon(R.drawable.bookstoreicon)
                .setLargeIcon(largeicon)
                .setContentTitle("Book added for sale")
                .setContentText("Your item has been added fpr sale")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("The book details have been uploaded successfully."))
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        manager.notify(null, 3, builder);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //    integration of seller after a submission
    public void openPayment(View view) {
        Intent i5 = new Intent(Seller.this, Seller.class);
        startActivity(i5);
    }

    //    validating phonenumber
    private boolean isValidMobile(EditText text) {
        CharSequence phone = text.getText().toString();
        return phone.length() == 10;

    }

    //    validating pincode
    private boolean isValidpincode(EditText text) {
        CharSequence phone = text.getText().toString();
        return phone.length() == 6;

    }

    private void addbook(){


//        storing bookdatabase
        book_name.setValue(editText.getText().toString());
        book_author.setValue(editText2.getText().toString());
        book_edition.setValue(editText4.getText().toString());
        //Integer adc = book_edition.push().setValue(Integer.class);
        book_publisher.setValue(editText5.getText().toString());
        book_price.setValue(editText3.getText().toString());
        sel_society.setValue(editText7.getText().toString());
        sel_district.setValue(editText8.getText().toString());
        sel_pincode.setValue(editText9.getText().toString());
        sel_state.setValue(editText10.getText().toString());
        sel_phn.setValue(editText11.getText().toString());
        //rb.setRating(rat);
        book_rating.setValue(rb.getRating());
        book_branch.setValue(item);
        String value = "0";
        flag.setValue(value);
        book_key.setValue(key);
        user_key.setValue(currentFirebaseUser.getUid());

        //Upload Image
        if(filePath != null)
        {
          /*  final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();*/

            StorageReference ref = storageReference.child("images/"+String.valueOf(key));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressDialog.dismiss();
                            Toast.makeText(Seller.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(Seller.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                           // progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }



}



//Veena Nagar, Vikas B N(Notification)
