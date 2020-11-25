package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {


    Button logout, viewMyProfile, restaurant, streetFood, searchBtn;
    TextView welcomeFullName;
    ImageView restaurantPic, streetFoodPic;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout = findViewById(R.id.btn_dashboard_logout);
        welcomeFullName = findViewById(R.id.tv_dashboard_welcome);
        viewMyProfile = findViewById(R.id.btn_dashboard_viewprofile);
        restaurantPic = findViewById(R.id.iv_dashboard_restaurant);
        restaurant = findViewById(R.id.btn_dashboard_restaurant);
        streetFoodPic = findViewById(R.id.iv_dashboard_sf);
        streetFood = findViewById(R.id.btn_dashboard_sf);
        search = findViewById(R.id.et_dashboard_search);
        searchBtn = findViewById(R.id.btn_dashboard_search);

    }
}