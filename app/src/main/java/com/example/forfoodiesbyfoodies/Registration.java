package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This Registration class is responsible to insert new users into the Firebase authorisation
 * database to check if a username (email address) is already registered in the database and
 * to handle registration process error such as network issues, missing mandatory details. */
public class Registration extends AppCompatActivity {

    // These attributes are essential user details
    EditText email, firstName, lastName, password;
    // These attributes for feedback to the user (errors, missing details)
    TextView emailWarning, firstNameWarning, lastNameWarning, passwordWarning;
    // This attribute stands for the "register" button to start different database and evaluation tasks
    Button register;
    // The following attributes assist for database and other technical workouts
    DatabaseReference dbRef;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    /* This object will be initialised to store and deliver the registered details:
     * - to the next activity
     * - into the Firebase Realtime database*/
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Linking the views of Registration activity to this code
        email = findViewById(R.id.et_registration_email);
        emailWarning = findViewById(R.id.tv_registration_email_warning);
        firstName = findViewById(R.id.et_registration_firstname);
        firstNameWarning = findViewById(R.id.tv_registration_firstname_warning);
        lastName = findViewById(R.id.et_registration_lastname);
        lastNameWarning = findViewById(R.id.tv_registration_lastname_warning);
        password = findViewById(R.id.et_registration_password);
        passwordWarning = findViewById(R.id.tv_registration_password_warning);
        register = findViewById(R.id.btn_registration_register);

        // Initialisation of database and authentication objects (linking the code to the DBs)
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        // Linking the app to the local file-based information
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        /* Switching the Register button to be inactive by default
         * (will be enable after registration requirements are satisfied) */
        register.setEnabled(false);

        // firstName field checker then feedback if not provided
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // every time the text has been changed, the warning text and color changed are changed depending on the statement satisfied
                if (firstName.getText().toString().length() > 0) {
                    firstNameWarning.setTextColor(getResources().getColor(R.color.teal_700));
                } else {
                    firstNameWarning.setTextColor(getResources().getColor(R.color.red));
                }

            }
        });

        // lastName field checker then feedback if not provided
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /* every time the text has been changed, the warning text and
             * color changed are changed depending on the statement satisfied  */
            @Override
            public void afterTextChanged(Editable s) {
                if (lastName.getText().toString().length() > 0) {
                    lastNameWarning.setTextColor(getResources().getColor(R.color.teal_700));
                } else {
                    lastNameWarning.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });

        /* Email and warning message fields manipulation to feedback if
         * - the given e-mail is logically all right or not
         * - give proper error message back to user if the email look incorrect logically */
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /* every time the text has been changed, the warning text and
             * color are changed depending on the statement satisfied  */
            @Override
            public void afterTextChanged(Editable s) {
                if (!isLogicalEmail(email.getText().toString())) {
                    emailWarning.setText("is not looking like a valid e-mail yet");
                } else {
                    emailWarning.setText("Thank you!");
                    emailWarning.setTextColor(getResources().getColor(R.color.teal_700));
                }
            }
        });

        /* password and warning messages fields manipulation to:
         * - call proper methods to check real-time if the password contains the requirements
         * - disables the register button until the password given is not OK
         * - changes warning message color and text */
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /* every time the password typed has been changed, the warning text and
             * color are changed depending on the statement satisfied  */
            @Override
            public void afterTextChanged(Editable s) {
                if (!pswChecker(password.getText().toString())) {
                    passwordWarning.setText("Password is not strong enough!");
                    passwordWarning.setTextColor(getResources().getColor(R.color.red));
                    register.setEnabled(false);
                } else {
                    passwordWarning.setText("The password looks fine!");
                    passwordWarning.setTextColor(getResources().getColor(R.color.teal_700));
                    register.setEnabled(true);
                }
            }
        });

        // The REGISTER button on click event listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store some details from views to work with them in an easier way
                String givenEmail = email.getText().toString();
                String givenFN = firstName.getText().toString();
                String givenLN = lastName.getText().toString();
                String givenPSW = password.getText().toString();

                /* If email is logical, password meets the requirements and
                 * names are given then try to submit into Firebase Authentication */
                if (isLogicalEmail(givenEmail) && givenFN.length() > 0 && givenLN.length() > 0 && pswChecker(givenPSW)) {
                    // adding new user to Firebase Authentication
                    auth.createUserWithEmailAndPassword(givenEmail, givenPSW)
                            // adding a listener to check if the addition process was successful or not
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    /* If addition to the Authentication DB is completed successfully then
                                     * - inform user with a message of success */
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registration.this,
                                                "Registration is successful!\nWelcome to the For Foodies by Foodies App", Toast.LENGTH_LONG).show();
                                        /* Adding the email, first and last names with a default "user" user type to a User object
                                         * pushing this User type object with all the details into the Realtime DB */
                                        user = new User(givenEmail, givenFN, givenLN, "user", null);
                                        dbRef.child(dbRef.push().getKey()).setValue(user)
                                                /* If pushing data into Realtime DB completed successfully then
                                                 * - create a SharedPreference that provides local file-based data storage/database technique
                                                 * - put the created User object into the intent and start the Dashboard */
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        sharedPreferences.edit().putBoolean("logged", true).apply();
                                                        sharedPreferences.edit().putString("username", givenEmail).apply();
                                                        Intent i = new Intent(Registration.this, Dashboard.class);
                                                        i.putExtra("user", user);
                                                        startActivity(i);
                                                    }
                                                })
                                                /* If pushing data in Realtime DB failed because of network issue then warn the user */
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Registration.this, "Network issues, please try again later", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    } else {
                                        // If username (email) is already registered then warn the user
                                        Toast.makeText(Registration.this, "This email is already registered!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    // If either email or password is wrong then warning the user
                    Toast.makeText(Registration.this, "Check your details and try again please!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // This method evaluates if the given email fits to the logic implemented in the Patterns library
    private boolean isLogicalEmail(CharSequence emailToCheck) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailToCheck).matches();
    }

    // This method is called to check if the password given by the user meets or does not the requirements
    public boolean pswChecker(final String psw) {
        Pattern pattern;
        Matcher matcher;

        /* ^ means start of string
         * (?=.*[0-9]) means 1 numeric at least
         * (?=.*[a-z]) means 1 lowercase at least
         * (?=.*[A-Z]) means 1 uppercase at least
         * (?=\S+$) means no spaces/whitespace allowed
         * .{6,} means 6 characters at least */
        final String PSW_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        // Chaining remaining non-satisfied requirements.
        String requirements = "Please provide a password that contains:\n";

        pattern = Pattern.compile(PSW_PATTERN);
        matcher = pattern.matcher(psw);

        return matcher.matches();
    }

}
