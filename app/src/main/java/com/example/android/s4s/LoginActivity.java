
//Sai Girish(Major), Vasudev B M(Minor), Vikas B N(Notification)

package com.example.android.s4s;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static com.example.android.s4s.MainActivity.CHANNELid1;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText mEmailField;
    EditText mPasswordField;
    TextView forgot_password;
    TextInputLayout email_layout, password_layout;
    Button login;
    NotificationManagerCompat manager;

    LoginButton loginButton;
    private CallbackManager callbackManager;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    SharedPreferences sp, sp2, sp1;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "LogInActivity";
    private static final int RC_SIGN_IN = 9001;
    SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseDatabase database;
    private DatabaseReference Ref_name, Ref_email, Ref_phone, Ref_password;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {

        callbackManager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sp = getSharedPreferences("login",
                MODE_PRIVATE);

        manager = NotificationManagerCompat.from(this);


        mEmailField = findViewById(R.id.email_login_text_view);
        mPasswordField = findViewById(R.id.password_login_text_view);
        email_layout = findViewById(R.id.layout_login_email);
        password_layout = findViewById(R.id.layout_login_password);
        login = findViewById(R.id.login_button);
        forgot_password = findViewById(R.id.forgot_password);

        dialog = new ProgressDialog(this);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        if (user == null) {
//            Toast.makeText(getApplicationContext(), "No User Exists", Toast.LENGTH_SHORT).show();
//        }


        signInButton = findViewById(R.id.sign_in_button);
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id2))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        /**
         * email and public_profile can be read from facebook,
         * on clicking facebook login button
         */
        loginButton = findViewById(R.id.login_button_fb);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));


        /**
         * Sign-in is continued with facebook
         */


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(LoginActivity.this, "Facebook Login Successful",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Facebook Login Cancelled",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "Facebook Login Error",
                        Toast.LENGTH_SHORT).show();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Facebook Login Successful",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        sp.edit().putBoolean("logged", true).apply();
                        sp1.edit().putString("uid", mAuth.getUid()).apply();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        assert user != null;
                        if (user != null) {
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            database.getReference().child("User").child(mAuth.getCurrentUser().getUid()).child("name").setValue(name);
                            database.getReference().child("User").child(mAuth.getCurrentUser().getUid()).child("email").setValue(email);

//                            Ref_name.setValue(name);
//                            Ref_email.setValue(email);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, "Facebook Login Cancelled",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Facebook Login Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });


        /**
         * Declaration and linking of views
         */


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(LoginActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        new ContextThemeWrapper(LoginActivity.this, R.style.AlertDialogCustom));

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        sendPasswordReset(userInput.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        /**
         * When login button is clicked ,
         * checkDataEntered() is evaluated
         */
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!validateForm())
//                {
//                    return;
//                }
//                sign_in(mEmailField.getText().toString(), mPasswordField.getText().toString());
//            }
//        });

    }


    public void sendPasswordReset(String email) {
        // [START send_password_reset]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "Email sent.");
                            sendpwdchange();

                        }
                    }
                });
        // [END send_password_reset]
    }


    public void sendpwdchange() {
        //Intent activityIntent =new Intent(getApplicationContext(),Wishlist.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,activityIntent,0);
        Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.bpicon);
        @SuppressWarnings("deprecations")
        Notification builder = new NotificationCompat.Builder(getApplicationContext(), CHANNELid1)
                .setSmallIcon(R.drawable.bookstoreicon)
                .setLargeIcon(largeicon)
                .setContentTitle("Password reset")
                .setContentText("Your 'reset password' link has been sent")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("A reset password link has been sent to your registered e-mail ID."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        manager.notify(null, 3, builder);
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();

    }
    // [END on_start_check_user]

    @Override
    public void onStop() {
        super.onStop();

    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                assert account != null;
//                String personName = account.getDisplayName();
//                String personEmail = account.getEmail();
//                Ref_name.setValue(personName);
//                Ref_email.setValue(personEmail);
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                //Log.e(TAG, "Google Sign In failed.");
                String s = result.getStatus().getStatusMessage();
                Log.d(TAG, "handleSignInResult:" + result.getStatus().toString());
                Toast.makeText(LoginActivity.this, "Google SignIn Failed",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    // [END onactivityresult]

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolved error has occured and Google APIs  (including Sign-In) will not
        // be available.
        Toast.makeText(LoginActivity.this, "Google Connection Lost",
                Toast.LENGTH_SHORT).show();
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(LoginActivity.this, "Google Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            sp2 = getSharedPreferences("temp", MODE_PRIVATE);
                            sp2.edit().putBoolean(FirebaseAuth.getInstance().getCurrentUser().getUid(), true).apply();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            sp.edit().putBoolean("logged", true).apply();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Google Login Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    // [END auth_with_google]

    //Facebook Authentication
    private void firebaseAuthWithFacebook(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Facebook Login Successful.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            sp2 = getSharedPreferences("temp", MODE_PRIVATE);
                            sp2.edit().putBoolean(FirebaseAuth.getInstance().getCurrentUser().getUid(), true).apply();
                            finish();
                        }
                    }
                });
    }


    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this, "Google Signout",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * To check the validity of all the views
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_layout.setError("Required.");
            valid = false;
        } else {
            email_layout.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            password_layout.setError("Required.");
            valid = false;
        } else {
            password_layout.setError(null);
        }

        return valid;
    }


    /**
     * openTerms() is evaluated when user clicks <u>terms</u>,
     * Terms and conditions are displayed
     *
     * @param view
     */
    public void openTerms(View view) {
        Intent i = new Intent(this, TermsActivity.class);
        startActivity(i);
    }


    /**
     * To check the status of radio button
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_create_account:
                if (checked) {
                    Intent i = new Intent(this, RegistrationActivity.class);
                    startActivity(i);
                    break;
                }
            case R.id.radio_login:
                if (checked) {
                    break;
                }
        }
    }


    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    public void sign_in(View v) {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        dialog.setMessage("Signing In...");
        dialog.show();
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            dialog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "Login Successful",
                                        Toast.LENGTH_SHORT).show();

                                DatabaseReference rootRef, userRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();
                                //mStorage = FirebaseStorage.getInstance().getReference("Profile Pics");
                                userRef = rootRef.child("User").child(mAuth.getUid());


                                userRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String type = dataSnapshot.child("type").getValue(String.class);
                                        assert type != null;
                                        int userType = Integer.parseInt(type);


                                        switch (userType) {
                                            case 1:
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                sp.edit().putBoolean("logged", true).apply();
                                                startActivity(intent);
                                                break;
                                            case 2:
                                                Intent intent1 = new Intent(LoginActivity.this, AdminActivity.class);
                                                sp.edit().putBoolean("logged", true).apply();
                                                startActivity(intent1);
                                                break;

                                        }
                                        sp.edit().putBoolean("logged", true).apply();
                                        sp2 = getSharedPreferences("temp", MODE_PRIVATE);
                                        sp2.edit().putBoolean(FirebaseAuth.getInstance().getCurrentUser().getUid(), true).apply();


//                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(i);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                });
                            } else {
                                Toast.makeText(LoginActivity.this, "Verify Email Sent.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Sign in Failed.\nEnter Valid Email and Password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });


    }
}

//Sai Girish(Major), Vasudev B M(Minor), Vikas B N(Notification)
