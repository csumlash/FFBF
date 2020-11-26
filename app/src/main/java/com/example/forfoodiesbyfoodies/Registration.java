package com.example.forfoodiesbyfoodies;

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
    private EditText email, firstName, lastName, password;
    // These attributes for feedback to the user (errors, missing details)
    private TextView emailWarning, firstNameWarning, lastNameWarning, passwordWarning;
    // This attribute stands for the "register" button to start different database and evaluation tasks
    private Button register;
    // The following attributes assist for database and other technical workouts
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();

        register.setEnabled(false);

        /* email and warning message fields manipulation to check if
         * - the given e-mail is logically all right or not
         * - give proper error message back to user if the email is not correct logically */
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

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

        /* the password and warning messages fields manipulation to:
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

            @Override
            public void afterTextChanged(Editable s) {
                if (!pswChecker(password.getText().toString())) {
                    passwordWarning.setTextColor(getResources().getColor(R.color.red));
                    register.setEnabled(false);
                } else {
                    passwordWarning.setText("The password looks correct!");
                    passwordWarning.setTextColor(getResources().getColor(R.color.teal_700));
                    register.setEnabled(true);
                }
            }
        });

        // The REGISTER button on click event listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* If email is logical, password meets the requirements and
                 * names are given then try to submit into Firebase Authentication */
                String givenEmail = email.getText().toString();
                String givenFN = firstName.getText().toString();
                String givenLN = lastName.getText().toString();
                String givenPSW = password.getText().toString();

                if (isLogicalEmail(givenEmail) == true && givenFN.length() > 0 &&
                        givenLN.length() > 0 && pswChecker(givenPSW) == true) {
                    auth.createUserWithEmailAndPassword(givenEmail, givenPSW)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registration.this, "Registration is successful!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Registration.this, "This email is already registered!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registration.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(Registration.this, "Check your details and try again please!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // this method evaluates if the given email fits to the logic implemented in the Patterns library
    private boolean isLogicalEmail(CharSequence emailToCheck) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailToCheck).matches();
    }

    // this method is called to check if the password given by the user is OK or not
    public boolean pswChecker(final String psw) {

        Pattern pattern;
        Matcher matcher;

        /*
         * ^ means start of string
         * (?=.*[0-9]) means 1 numeric at least
         * (?=.*[a-z]) means 1 lowercase at least
         * (?=.*[A-Z]) means 1 uppercase at least
         * (?=\S+$) means no spaces/whitespace allowed
         * .{6,} means 6 characters at least
         */
        final String PSW_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PSW_PATTERN);
        matcher = pattern.matcher(psw);

        return matcher.matches();
    }

}
