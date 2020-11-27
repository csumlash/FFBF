package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    // Defining the view elements
    Button logout, viewMyProfile, restaurant, streetFood, searchBtn;
    TextView welcomeFullName;
    ImageView restaurantPic, streetFoodPic;
    EditText search;

    // A User type object to store the User object got from the previous (Login and Registration) activities
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Linking the view elements to the variables in this code
        logout = findViewById(R.id.btn_dashboard_logout);
        welcomeFullName = findViewById(R.id.tv_dashboard_welcome);
        viewMyProfile = findViewById(R.id.btn_dashboard_viewprofile);
        restaurantPic = findViewById(R.id.iv_dashboard_restaurant);
        restaurant = findViewById(R.id.btn_dashboard_restaurant);
        streetFoodPic = findViewById(R.id.iv_dashboard_sf);
        streetFood = findViewById(R.id.btn_dashboard_sf);
        search = findViewById(R.id.et_dashboard_search);
        searchBtn = findViewById(R.id.btn_dashboard_search);

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        // Setting up the welcome message
        //String firstName = user.getFirstName();
        //String lastName = user.getLastName();
        welcomeFullName.setText("Welcome ");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                startActivity(new Intent(Dashboard.this, Login.class));
            }
        });

    }
}