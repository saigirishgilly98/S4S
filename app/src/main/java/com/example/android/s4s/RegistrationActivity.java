//Sai Girish(Major), Vasudev B M(Minor)
package com.example.android.s4s;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {

    /**
     * Declaration of views
     */
    EditText name;
    EditText email;
    EditText phone;
    EditText password;
    EditText confirmpassword;
    TextInputLayout name_layout, email_layout, phone_layout, password_layout, confirm_layout;
    Button register;
    FirebaseAuth.AuthStateListener authStateListener;

    SharedPreferences sp, sp1;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog = new ProgressDialog(this);


        name = findViewById(R.id.name_text_view);
        email = findViewById(R.id.email_text_view);
        phone = findViewById(R.id.phone_text_view);
        password = findViewById(R.id.password_text_view);
        confirmpassword = findViewById(R.id.confirm_password_text_view);
        name_layout = findViewById(R.id.layout_name);
        email_layout = findViewById(R.id.layout_email);
        phone_layout = findViewById(R.id.layout_phone);
        password_layout = findViewById(R.id.layout_password);
        confirm_layout = findViewById(R.id.layout_confirm);
        register = findViewById(R.id.register_button);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
        }


        database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        assert ref.getKey() != null;

        mAuth = FirebaseAuth.getInstance();


        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (isEmpty(name)) {
                        name_layout.setError("Name is required!");
                    } else
                        name_layout.setError(null);
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isEmail(email)) {
                        email_layout.setError("Enter valid email!");
                    } else
                        email_layout.setError(null);
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isValidMobile(phone)) {
                        phone_layout.setError("Phone Number must have 10 digits!");
                    } else
                        phone_layout.setError(null);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isValidPassword(password)) {
                        password_layout.setError("Password must be at least 8 characters!");
                    } else
                        password_layout.setError(null);
                }
            }
        });

        confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
                        confirm_layout.setError("Password did not match!");
                    } else
                        confirm_layout.setError(null);
                }
            }
        });

    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private boolean validateForm() {
        boolean valid = true;
        /**
         * Error display for invalid name
         */
        if (isEmpty(name)) {
            name_layout.setError("Name is required!");
            valid = false;
        } else
            name_layout.setError(null);

        /**
         * Error display for invalid email
         */
        if (!isEmail(email)) {
            email_layout.setError("Enter valid email!");
            valid = false;
        } else
            email_layout.setError(null);

        /**
         * Error display for invalid phone
         */
        if (!isValidMobile(phone)) {
            phone_layout.setError("Phone Number must have 10 digits!");
            valid = false;
        } else
            phone_layout.setError(null);

        /**
         * Error display for invalid password
         */
        if (!isValidPassword(password)) {
            password_layout.setError("Password must be at least 8 characters!");
            valid = false;
        } else
            password_layout.setError(null);

        /**
         * Password entered in confirm password view is checked for a match ,
         * with already entered password
         */
        if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
            confirm_layout.setError("Password did not match!");
            valid = false;
        } else
            confirm_layout.setError(null);

        return valid;
    }

    /**
     * To check the validation of Email address
     *
     * @param text
     * @return '1' for accepted email and '0' for not accepted email
     */
    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * To Check if EditText View is empty
     *
     * @param text
     * @return '1' for empty and '0' for not empty
     */
    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    /**
     * To check the validation of Phone Number
     *
     * @param text
     * @return '1' if valid '0' if not valid
     */
    private boolean isValidMobile(EditText text) {
        CharSequence phone = text.getText().toString();
        return phone.length() == 10;

    }

    /**
     * To  check the validation of Password
     *
     * @param text
     * @return '1' if valid '0' if not valid
     */
    public static boolean isValidPassword(EditText text) {
        CharSequence password = text.getText().toString();
        return password.length() >= 8;
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
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_login:
                if (checked) {
                    Intent i = new Intent(this, LoginActivity.class);
                    startActivity(i);
                    break;
                }
        }
    }


    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            mAuth.signOut();
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

    public void sign_up(View v) {

        final String em = email.getText().toString();
        final String pw = password.getText().toString();
        final String na = name.getText().toString();
        final String ph = phone.getText().toString();

        if (em.equals("") || pw.equals("")) {
            Toast.makeText(getApplicationContext(), "Email or Password is blank!!", Toast.LENGTH_SHORT).show();

        } else {
            progressDialog.setMessage("Registering...Please Wait");
            progressDialog.show();

            final int val = 0;
            mAuth.createUserWithEmailAndPassword(em, pw)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.hide();

                                database.getReference().child("User").child(mAuth.getUid()).child("email").setValue(em);
                                database.getReference().child("User").child(mAuth.getUid()).child("name").setValue(name);
                                database.getReference().child("User").child(mAuth.getUid()).child("password").setValue(pw);
                                database.getReference().child("User").child(mAuth.getUid()).child("phone").setValue(ph);
                                database.getReference().child("User").child(mAuth.getUid()).child("url").setValue("https://www.flaticon.com/free-icon/man_236831.jpg");
                                database.getReference().child("User").child(mAuth.getUid()).child("balance").setValue("1000");
                                database.getReference().child("User").child(mAuth.getUid()).child("ads").setValue(val);
                                database.getReference().child("User").child(mAuth.getUid()).child("type").setValue("1");

                                String num = database.getReference().child("User").child("nlkd9s7YB1OfBBE257shUHjefut2").child("number").toString();
                                int number = Integer.parseInt(num);
                                number++;

                                String num1 = Integer.toString(number);
                                database.getReference().child("User").child("nlkd9s7YB1OfBBE257shUHjefut2").child("number").setValue(num1);


                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();


                                FirebaseUser user = mAuth.getCurrentUser();
                                /* [START send_email_verification] */

                                if (user != null) {
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        //Log.d(TAG, "Email sent.");
                                                        Toast.makeText(RegistrationActivity.this, "Email Sent.",
                                                                Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                    // [END send_email_verification]
                                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegistrationActivity.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });

        }
    }
}


//Sai Girish(Major), Vasudev B M(Minor)


