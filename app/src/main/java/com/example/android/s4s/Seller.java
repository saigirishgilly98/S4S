package com.example.android.s4s;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Seller extends AppCompatActivity {
    private static final String TAG = "FragmentActivity";

    // ImageView uploadfront, uploadback;
    Button  button;
    AlertDialog.Builder builder;
    Intent I;
    EditText editText, editText2, editText3, editText4, editText5, editText7, editText8, editText9, editText10,editText11;
    TextInputLayout name_layout, author_layout, phone1_layout, edition_layout, price_layout,publisher_layout,
            locality_layout, district_layout, state_layout, pincode_layout, upload_layout,upload1_layout;
    private DatabaseReference book_name, book_author, book_edition, book_publisher, book_price, book_rating,
            sel_society, sel_district, sel_pincode, sel_phn, sel_state,flag;

    private FirebaseDatabase databasebook;
    private FirebaseAuth mAuth;

    private static int index = 0;
    ImageView viewImage,viewImage2;
    Button b,b2;
    ////// java class for adding profile photo
    private Uri selectedImage,selectedImage1;

    int PLACE_PICKER_REQUEST = 5;

    ///to store image

    private FirebaseStorage mstorage;

    private StorageReference store;

    private StorageReference storageReference;

    private DatabaseReference databaseReference;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    //
    DatabaseReference data;

    //
    public static final String FB_STORAGE_PATH = "image/";

    public static final String FB_DATABASE_PATH = "image";

    int i1;
    String key;
    TextView tvplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder ();
        StrictMode.setVmPolicy (builder1.build ());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //int i1;

        //uploadfront = (ImageView) findViewById(R.id.uploadfront);
        builder = new AlertDialog.Builder(this);
        // uploadback = (ImageView) findViewById(R.id.uploadback);
        //textView4 = (Button) findViewById(R.id.textView4);
        //textView5 = (Button) findViewById(R.id.textView5);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText7 = (EditText) findViewById(R.id.editText7);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText10 = (EditText) findViewById(R.id.editText10);
        editText11 = (EditText) findViewById(R.id.editText11);
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
        tvplace = (TextView) findViewById(R.id.tvplace);


        int i = index;
        index++;
        databasebook=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        databaseReference = FirebaseDatabase.getInstance().getReference("Seller");
        key = databaseReference.push().getKey();

        book_name = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Book Name");
        book_author = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Author's Name");
        book_edition = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Book Edition");
        book_publisher = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Publisher");
        book_price = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Price");
        //book_rating = databasebook.getReference("Seller").child("Book");
        sel_society = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Society");
        sel_district = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("District");
        sel_pincode = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Pincode");
        sel_state = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("State");
        sel_phn = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Contact details");
        flag = databasebook.getReference("Seller").child(currentFirebaseUser.getUid()).child(String.valueOf(key)).child("Flag");

        //below content is for placing backbutton in th toolbar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        //////////
        store = FirebaseStorage.getInstance ().getReference ();
        data = FirebaseDatabase.getInstance ().getReference ();

        //////////


        b = (Button) findViewById (R.id.btnSelectPhoto);
        viewImage = (ImageView) findViewById (R.id.frontpic);
        b2 = (Button) findViewById (R.id.btnSelectPhoto2);
        viewImage2 = (ImageView) findViewById (R.id.backpic);


        b.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                i1=1;
                selectImage ();
            }
        });
        b2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                i1=2;
                selectImage1 ();
            }
        });


        // below code is the validation code for the details
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    name_layout.setError("Name of the book is req");

                }
                else if (editText2.getText().toString().equals("")) {
                    author_layout.setError("Author's Name is req");

                }
                else if (editText3.getText().toString().equals("")) {
                    price_layout.setError("Expected price is req");

                }
                else if (editText4.getText().toString().equals("")) {
                    edition_layout.setError("Edition is req");

                }
                else if (editText5.getText().toString().equals("")) {
                    publisher_layout.setError("Publisher's name is req");

                }
                else if (editText7.getText().toString().equals("")) {
                    locality_layout.setError("enter you apartment/society/village name is req");
                }
                else if (editText8.getText().toString().equals("")) {
                    district_layout.setError("District name is req");

                }
                else if (editText9.getText().toString().equals("")) {
                    pincode_layout.setError("Pincode is req");
                }
                else if (!isValidpincode(editText9)) {
                    pincode_layout.setError("Pincode is wrong");

                }


                else if (editText10.getText().toString().equals("")) {
                    state_layout.setError("State name is req");

                }
                else if (editText11.getText().toString().equals("")) {
                    phone1_layout.setError("phone no. req");

                }
                else if (!isValidMobile(editText11)) {
                    phone1_layout.setError("phone no is wrong");

                }


                if(!editText.getText().toString().equals("") && !editText2.getText().toString().equals("") &&
                        !editText3.getText().toString().equals("") && !editText4.getText().toString().equals("") &&
                        !editText5.getText().toString().equals("") && !editText7.getText().toString().equals("") &&
                        !editText8.getText().toString().equals("") && !editText9.getText().toString().equals("") &&
                        !editText10.getText().toString().equals("") && !editText11.getText().toString().equals("") &&
                        isValidMobile(editText11) && isValidpincode(editText9)) {

                    builder.setMessage("add_for_sale").setTitle("add_for_sale");
                    builder.setMessage("Do you want to sale the book??")
                            .setCancelable(false)

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();

                                    addbook();
                                    Toast.makeText(getApplicationContext(), "Book is added for sale!!",
                                            Toast.LENGTH_SHORT).show();
                                    openPayment(findViewById(R.id.button));
                                    finish ();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Book is not added for sale!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Seller.this, Seller.class);

                                }
                            });
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Confirm");
                    alert.show();

                }


            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    public void openPayment(View view)
    {
        Intent i = new Intent(Seller.this, Seller.class);
        startActivity(i);
    }
    private boolean isValidMobile(EditText text) {
        CharSequence phone = text.getText().toString();
        return phone.length() == 10;

    }
    private boolean isValidpincode(EditText text) {
        CharSequence phone = text.getText().toString();
        return phone.length() == 6;

    }
    private void addbook(){


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
        String value = "0";
        flag.setValue(value);


        //3c2
        final ImageView test = (ImageView) findViewById (R.id.frontpic); //image stored here
        final ImageView test1 = (ImageView) findViewById (R.id.backpic);
        final Bitmap bmap = ((BitmapDrawable) test.getDrawable ()).getBitmap ();
        final Bitmap bmap1 = ((BitmapDrawable) test1.getDrawable ()).getBitmap ();
        Drawable myDrawable = getResources ().getDrawable (R.drawable.bookstoreicon);
        final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap ();


        if (bmap.sameAs (myLogo)) {
            //   k--;
            upload_layout.setError("Photo is req");

        } else {
            upload_image ();
            Toast.makeText (Seller.this, "what the ",Toast.LENGTH_SHORT).show ();
//                    Intent myIntent = new Intent (seller1.this,
//                            Negotiator_final.class);
//                    startActivity (myIntent);
//                    finish ();

        }
        if (bmap1.sameAs (myLogo)) {
            //   k--;
            upload1_layout.setError("Photo is req");

        } else {
            upload_image1 ();
            Toast.makeText (Seller.this, "what the ",Toast.LENGTH_SHORT).show ();
//                    Intent myIntent = new Intent (seller1.this,
//                            Negotiator_final.class);
//                    startActivity (myIntent);
//                    finish ();

        }

    }


    private void selectImage() {


        final CharSequence[] options = {"Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder1 = new AlertDialog.Builder (Seller.this);

        builder1.setTitle ("Add Photo!");

        builder1.setItems (options, new DialogInterface.OnClickListener () {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals ("Take Photo")) {

                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);


                    File f = new File (Environment.getExternalStorageDirectory (), "temp.jpg");


                    intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));

                    startActivityForResult (intent, 1);

                } else if (options[item].equals ("Choose from Gallery")) {

                    Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult (intent, 2);


                } else if (options[item].equals ("Cancel")) {

                    dialog.dismiss ();

                }

            }

        });

        builder1.show ();

    }

    private void selectImage1() {


        final CharSequence[] options = {"Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder1 = new AlertDialog.Builder (Seller.this);

        builder1.setTitle ("Add Photo!");

        builder1.setItems (options, new DialogInterface.OnClickListener () {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals ("Take Photo")) {

                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);


                    File f = new File (Environment.getExternalStorageDirectory (), "temp1.jpg");


                    intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));

                    startActivityForResult(intent, 3);

                } else if (options[item].equals ("Choose from Gallery")) {

                    Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult (intent, 2);


                } else if (options[item].equals ("Cancel")) {

                    dialog.dismiss ();

                }

            }

        });

        builder1.show ();

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("ssasad", "RESULTCODE:" + Integer.toString(requestCode));

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("Place: %s", place.getAddress());
                Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                tvplace.setText(place.getAddress());
            }
        }

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {


                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        // adding new peice of code here
                        selectedImage = Uri.fromFile(new File(f.toString()));

                        ///

                        break;

                    }
                }


                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);


                    viewImage.setImageBitmap(bitmap);


                    String path = Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(key) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }


            } else if (requestCode == 3) {
                File f1 = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp1 : f1.listFiles()) {
                    if (temp1.getName().equals("temp1.jpg")) {

                        f1 = temp1;

                        // adding new peice of code here
                        selectedImage1 = Uri.fromFile(new File(f1.toString()));

                        ///

                        break;

                    }
                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f1.getAbsolutePath(), bitmapOptions);

                    viewImage2.setImageBitmap(bitmap);

                    String path = Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f1.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(key) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }


            } else if (requestCode == 2) {

///// changed uri selectimage  to global variable
                if (i1 == 1) {

                    selectedImage = data.getData();

                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                    c.moveToFirst();

                    int columnIndex = c.getColumnIndex(filePath[0]);

                    String picturePath = c.getString(columnIndex);

                    c.close();

                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                    Log.w("pery", picturePath + "");
                    viewImage.setImageBitmap(thumbnail);
                }
                if (i1 == 2) {

                    selectedImage1 = data.getData();

                    String[] filePath1 = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver().query(selectedImage1, filePath1, null, null, null);

                    c.moveToFirst();

                    int columnIndex = c.getColumnIndex(filePath1[0]);

                    String picturePath = c.getString(columnIndex);

                    c.close();

                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                    Log.w("pery", picturePath + "");
                    viewImage2.setImageBitmap(thumbnail);
                }


            }


        }
    }



    /// adding code for negotiator profile photo upload

    private String getFileextension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver ();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton ();

        return mimeTypeMap.getExtensionFromMimeType (contentResolver.getType (uri)) ;
    }


    private void upload_image()
    {
        storageReference = FirebaseStorage.getInstance ().getReference ("Front page images");
        databaseReference= FirebaseDatabase.getInstance ().getReference ();

        if(selectedImage != null)
        {
            StorageReference mstorage = storageReference.child(currentFirebaseUser.getUid() + String.valueOf(key) + "." + getFileextension(selectedImage));

            mstorage.putFile (selectedImage).addOnSuccessListener (new OnSuccessListener <UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler ();
                    handler.postDelayed (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (Seller.this,"image uploaded",Toast.LENGTH_SHORT).show ();
                        }
                    }, 3000);



                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText (Seller.this,e.getMessage (),Toast.LENGTH_SHORT).show ();

                }
            }).addOnProgressListener (new OnProgressListener <UploadTask.TaskSnapshot> () {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress=(100.0 * taskSnapshot.getBytesTransferred () / taskSnapshot.getTotalByteCount ());

                }
            });

        }
        else
        {
            upload_layout.setError("Photo of the book is req");
        }
    }

    private void upload_image1()
    {
        storageReference = FirebaseStorage.getInstance ().getReference ("Back page images");
        databaseReference= FirebaseDatabase.getInstance ().getReference ();

        if(selectedImage1 != null)
        {
            StorageReference mstorage = storageReference.child(currentFirebaseUser.getUid() + String.valueOf(key) + "." + getFileextension(selectedImage1));

            mstorage.putFile (selectedImage1).addOnSuccessListener (new OnSuccessListener <UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler ();
                    handler.postDelayed (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText(Seller.this, "image2 uploaded", Toast.LENGTH_SHORT).show();
                        }
                    },5000);



                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText (Seller.this,e.getMessage (),Toast.LENGTH_SHORT).show ();

                }
            }).addOnProgressListener (new OnProgressListener <UploadTask.TaskSnapshot> () {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress=(100.0 * taskSnapshot.getBytesTransferred () / taskSnapshot.getTotalByteCount ());

                }
            });

        }
        else
        {
            upload1_layout.setError("Photo of the book is req");
        }
    }

    public void chooseplace(View view) {
        PlacePicker.IntentBuilder builder1 = new PlacePicker.IntentBuilder();
        try {
            // Intent  in = builder1.build((Activity) getApplicationContext());

            startActivityForResult(builder1.build(Seller.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage());
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage());
        }


    }

}
