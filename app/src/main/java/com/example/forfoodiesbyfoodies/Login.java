package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/* This class is responsible to give the power to the user to Login to the system or choose
 * the option to go to the registration to come back and login next times, in the future
 * The main responsibilities of the class:
 * - First of all to check if there is or is not any user logged in on the phone and if there is
 *   then automatically forward the user to the Dashboard activity with User object data.
 * - Accept username and password then login to the App by logging in with Firebase Authentication
 * - If any invalid details are given then reject login and handle the problems */
public class Login extends AppCompatActivity {

    // The following attributes will contain information are mandatory to perform a login
    EditText username, psw;
    // These button attributes will listen to the user interaction to do the login or registration process
    Button login, register;
    // The next attributes will be responsible to interact with the user if anything needs more attention
    TextView loginWarningMsg, registerMsg;
    // The attributes below will be technical ones to let the code perform actions and accept data to/from databases
    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    DatabaseReference dbRef;
    Query dbRefQuery;
    SharedPreferences sharedPreferences;
    // This object will store all the query data from user-related queries
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Linking the activity GUI views/elements to this code
        username = findViewById(R.id.et_log_email);
        psw = findViewById(R.id.et_log_password);
        login = findViewById(R.id.btn_log_login);
        loginWarningMsg = findViewById(R.id.tv_log_warning);
        registerMsg = findViewById(R.id.tv_log_regmessage);
        register = findViewById(R.id.btn_log_register);


        // initialising the sharedPreferences to read or write data into it for application use, login handling
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        // linking (initialising) the code to the Authentication, Realtime database and to Realtime node
        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();
        dbRef = rtdb.getReference("users");


        // The login button performs the declared actions in the login.setonClickListener(...){...};
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Local variables to store and then check the values are entered by the user
                String enteredName = username.getText().toString();
                String enteredPsw = psw.getText().toString();

                // Attempting to login the user by the entered name and password combination
                auth.signInWithEmailAndPassword(enteredName, enteredPsw)
                        // If the login process ended (even the login has not happened) successfully...
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    /* if the username and password are matching to any of the registered user records
                                     * then creating sub data into the SharedPreferences to mark the current user's
                                     * login state and username for further login handling and user queries then
                                     * forwarding the user to the Dashboard */
                                    sharedPreferences.edit().putBoolean("logged", true).apply();
                                    sharedPreferences.edit().putString("username", enteredName).apply();
                                    user = new User(sharedPreferences.getString("username",""), "idiotacska", "dinkacska", "user");
                                    Intent i = new Intent(Login.this, Dashboard.class);
                                    i.putExtra("user", user);
                                    startActivity(i);
                                } else {
                                    // if there is no username-password combination in the DB then warn the user
                                    loginWarningMsg.setText("Cannot login with these credentials");
                                    sharedPreferences.edit().putBoolean("logged", false).apply();
                                }
                            }
                        })
                        // Warning the user if the whole login attempt failed because of any network or device issue
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Network issues, please try again later!", Toast.LENGTH_LONG);
                            }
                        });
            }
        });

        // Pressing the Register button will send the user to the Registration activity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (sharedPreferences.getBoolean("logged", false)) {
            getUserDetails(sharedPreferences.getString("username", ""));
            Intent i = new Intent(Login.this, Dashboard.class);
            i.putExtra("user", user);
            startActivity(i);
        }
    }

    public void getUserDetails(String username) {
        dbRefQuery = dbRef.orderByChild("username").equalTo(username);
        dbRefQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}