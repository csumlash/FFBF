package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    // Defining the view elements
    Button logout, viewMyProfile, restaurants, streetFoodPlaces, searchBtn;
    TextView welcomeFullName, searchWarning;
    ImageView restaurantPic, streetFoodPic;
    EditText search;

    // A User type object to store the User object got from the previous (Login and Registration) activities
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        // Linking the view elements to the variables in this code
        logout = findViewById(R.id.btn_dashboard_logout);
        welcomeFullName = findViewById(R.id.tv_dashboard_welcome);
        viewMyProfile = findViewById(R.id.btn_dashboard_viewprofile);
        restaurantPic = findViewById(R.id.iv_dashboard_restaurant);
        restaurants = findViewById(R.id.btn_dashboard_restaurants);
        streetFoodPic = findViewById(R.id.iv_dashboard_sf);
        streetFoodPlaces = findViewById(R.id.btn_dashboard_sf);
        search = findViewById(R.id.et_dashboard_search);
        searchBtn = findViewById(R.id.btn_dashboard_search);
        searchWarning = findViewById(R.id.tv_dashboard_search_warning);

        // MUST BE CHANGED TO PROPER PICTURES!
        restaurantPic.setImageResource(R.drawable.ic_baseline_add_photo_image);
        streetFoodPic.setImageResource(R.drawable.ic_baseline_add_photo_image);

        // If the user logged in is admin then show user search field, button and warning message
        if (user.getUserType().equals("admin")) {
            search.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.VISIBLE);
            searchWarning.setVisibility(View.VISIBLE);
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userToFind = search.getText().toString();
                    Intent i = new Intent(Dashboard.this, Profile.class);
                    i.putExtra("username", userToFind);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            });
        }

        // Setting up the welcome message with first and last names
        welcomeFullName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());


        /* The logout button setup to destroy all the details stored in the SharedPreferences local
         * files and force the user to the login page */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                startActivity(new Intent(Dashboard.this, Login.class));
            }
        });

        // Setting up the View my profile button to navigate the user to their own profile
        viewMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Profile.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        // The Restaurants button to let the user get to the list of restaurants
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, RestaurantListOfRestaurants.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        // The Street Food Places button to let the user get to the list of Street Food Places
        streetFoodPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Dashboard.this, SfListOfStreetFoods.class);
                k.putExtra("user", user);
                startActivity(k);
            }
        });

    }
}