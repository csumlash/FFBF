package com.example.forfoodiesbyfoodies;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    // Getting the reference to the "users" node in the database to query and insert
    // The following attributes assist for database and other technical workouts
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Linking the views of activity to this code
        email = findViewById(R.id.et_registration_email);
        emailWarning = findViewById(R.id.tv_registration_email_warning);
        firstName = findViewById(R.id.et_registration_firstname);
        firstNameWarning = findViewById(R.id.tv_registration_firstname_warning);
        lastName = findViewById(R.id.et_registration_lastname);
        lastNameWarning = findViewById(R.id.tv_registration_lastname_warning);
        password = findViewById(R.id.et_registration_password);
        passwordWarning = findViewById(R.id.tv_registration_password_warning);
        register = findViewById(R.id.btn_registration_register);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isLogicalEmail(email.getText().toString())){
                    emailWarning.setText("is not looking like a valid e-mail yet");
                } else {
                    emailWarning.setText("Thank you!");
                    emailWarning.setTextColor(getResources().getColor(R.color.teal_700));
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!pswChecker(password.getText().toString())){
                    passwordWarning.setText("");
                    passwordWarning.setTextColor(getResources().getColor(R.color.red));
                }else{
                    passwordWarning.setText("The password looks correct!");
                    passwordWarning.setTextColor(getResources().getColor(R.color.teal_700));
                }
            }
        });

        // The REGISTER button on click event listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private boolean isLogicalEmail(CharSequence emailToCheck){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailToCheck).matches();
    }

    public boolean pswChecker(final String psw) {

        Pattern pattern;
        Matcher matcher;

        /*
         * ^ means start of string
         * (?=.*[0-9]) means 1 numeric at least
         * (?=.*[a-z]) means 1 lowercase at least
         * (?=.*[A-Z]) means 1 uppercase at least
         * (?=\S+$) means no spaces/whitespace allowed
         * .{8,} means 6 characters at least (+start and end of string so + 2 characters)
         */
        final String PSW_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PSW_PATTERN);
        matcher = pattern.matcher(psw);

        return matcher.matches();
    }

}
