package com.example.android.s4s;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.CAMERA;


public class ProfileEdit1 extends AppCompatActivity {

    private static final int ALL_PERMISSIONS_RESULT = 107;
    DatabaseReference databaseReference;
    EditText email_edit, phone_edit, name_edit;
    String phone, email, name;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;
    ProgressDialog dialog;
    AlertDialog.Builder builder;
    ImageView imageView1, imageView2;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String downloadUrl, url;
    // de.hdodenhof.circleimageview.CircleImageView pro;

    Bitmap myBitmap;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Uri picUri;


    private FirebaseDatabase database;
    DatabaseReference rootRef, userRef;

    private StorageReference mStorage, storageReference;
    private Uri selectedImage;
    int y = 0;
    String key;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Picasso.with(ProfileEdit1.this).load("https://firebasestorage.googleapis.com/v0/b/s4sproject.appspot.com/o/9Il3qyWBCdUhc2Akv02OeDoQ1Oc2.jpg?alt=media&token=28701b98-2867-4a73-a9f6-182fdc924eb7.jpg").fit().centerCrop().into(imageView1);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        name_edit = (EditText) findViewById(R.id.editname);
        phone_edit = (EditText) findViewById(R.id.editphone);
        email_edit = (EditText) findViewById(R.id.editmail);

        dialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        imageView1 = (ImageView) findViewById(R.id.profile_pic);
        imageView2 = (ImageView) findViewById(R.id.profile_editpic);
        //selectedImage = Uri.parse("https://png.pngtree.com/element_our/png_detail/20181102/avatar-profile-logo-vector-emblem-illustration-modern-illustration-png_227485.jpg");

        rootRef = FirebaseDatabase.getInstance().getReference();
        //mStorage = FirebaseStorage.getInstance().getReference("Profile Pics");
        userRef = rootRef.child("User").child(FirebaseAuth.getInstance().getUid());


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //  String name = dataSnapshot.child("name").getValue(String.class);
                //String email = dataSnapshot.child("email").getValue(String.class);
                //String phone = dataSnapshot.child("phone").getValue(String.class);


                String imageurl = dataSnapshot.child("url").getValue().toString();
                if (imageurl != " ")
                    Picasso.with(getApplicationContext()).load(imageurl).fit().centerCrop().into(imageView2);


                // Picasso.with(getApplicationContext()).load(imageurl).fit().centerCrop().into(imageView1);

                // Picasso.with(Profile1.this).load(mAuth.getUid()).into(imageView1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        key = mAuth.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid());
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        storageReference = FirebaseStorage.getInstance().getReference();


        final AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileEdit1.this);

        builder1.setTitle("Add Photo!");

        ImageView pic = (ImageView) findViewById(R.id.edit_pic);

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        databaseReference = FirebaseDatabase.getInstance().getReference();

        ImageButton ib = (ImageButton) findViewById(R.id.tick);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            @NonNull
            public void onClick(View v) {
                email = email_edit.getText().toString();
                phone = phone_edit.getText().toString();
                name = name_edit.getText().toString();


                rootRef = FirebaseDatabase.getInstance().getReference();
                userRef = rootRef.child("User");


                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        try {
                            if (!name.equals(""))
                                userRef.child((mAuth.getUid())).child("name").setValue(name);
                            if (!phone.equals(""))
                                userRef.child((mAuth.getUid())).child("phone").setValue(phone);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                            openProfile(imageView2);
                            Intent i = new Intent(ProfileEdit1.this, Profile1.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }

    public void onBackPressed() {
        Intent i = new Intent(this, Profile1.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void openProfile(View v) {

        imageView1 = imageView2;
    }

    private String getFileextension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            ImageView imageView = (ImageView) findViewById(R.id.profile_editpic);

            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);

                    ImageView croppedImageView = (ImageView) findViewById(R.id.profile_editpic);
                    croppedImageView.setImageBitmap(myBitmap);
                    imageView.setImageBitmap(myBitmap);
                    FirebaseUser cuser = mAuth.getCurrentUser();
                    String id = cuser.getUid();

                    final StorageReference mountainref = storageReference.child(id + ".jpg");

                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] dataphoto = baos.toByteArray();

                    UploadTask uploadTask = mountainref.putBytes(dataphoto);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri durl=taskSnapshot.getMetadata().getDo; //contains file metadata such as size, content-type, etc.
                            // ...
                            mountainref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(mAuth.getUid()).child("url");
                                    databaseReference.setValue(downloadUrl);

                                }
                            });
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;
                ImageView croppedImageView = (ImageView) findViewById(R.id.profile_editpic);
                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }

                imageView.setImageBitmap(myBitmap);

            }

        }

    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }


}
