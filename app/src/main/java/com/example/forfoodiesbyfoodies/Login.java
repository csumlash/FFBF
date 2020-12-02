package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    DatabaseReference dbRef;
    SharedPreferences sharedPreferences;
    // This object will store and be passed to other activities with all the data from user-related queries
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
        dbRef = FirebaseDatabase.getInstance().getReference("users");

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
                                     * login state and username for further login handling and user queries then */
                                    sharedPreferences.edit().putBoolean("logged", true).apply();
                                    sharedPreferences.edit().putString("username", enteredName).apply();
                                    /* calling the method that:
                                     *  - queries the other registered user details from Realtime Database
                                     *  - launching the Dashboard with put User object filled by details */
                                    startQueryThenDashboard(sharedPreferences.getString("username", ""));
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

    // After the application launched, checking if there is or is not user already logged in.
    @Override
    public void onStart() {
        super.onStart();
        // If the user is logged in then passing their stored username (email) to the method that makes user Query and start Dashboard
        if (sharedPreferences.getBoolean("logged", false)) {
            startQueryThenDashboard(sharedPreferences.getString("username", ""));
        }
    }

    /* This method is responsible to:
     *  - Query the user's firstName, lastName and userType (user, foodcrit, admin)
     *  - Build a User type object from query to store all the user details and add it to the intent (Dashboard) starter */
    public void startQueryThenDashboard(String username) {
        /* A simple query that has
         *  - orderByChild("username") => to sort/order the query by the username (email) values
         *  - equalTo(username) => requests only equal values to username got as method call parameter
         *  - limitToFirst(1) => the result will contain 1 record even there were more results in the query */
        Query usersQuery = dbRef.orderByChild("username").equalTo(username).limitToFirst(1);
        // Executing the query
        usersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Fetching data of query then inserting and so creating User object
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // The data gathering from snapshot of query
                    String username = ds.child("username").getValue().toString();
                    String firstName = ds.child("firstName").getValue().toString();
                    String lastName = ds.child("lastName").getValue().toString();
                    String userType = ds.child("userType").getValue().toString();
                    String profPicUrl;
                    try {
                        profPicUrl = ds.child("url").getValue().toString();
                    } catch (Exception e) {
                        profPicUrl = null;
                    }
                    // Creating the object with the fetched data
                    user = new User(username, firstName, lastName, userType, profPicUrl);
                    // Creating and filling intent with extra data to forward it to the started Dashboard activity
                    Intent i = new Intent(Login.this, Dashboard.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            }

            // If Query cannot be executed because of any network issue then warning the user
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Cannot login due to connection issues, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
}