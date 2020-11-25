package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class RestaurantListOfRestaurants extends AppCompatActivity {

    Button addrestaurant;
    RecyclerView restaurantlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_of_restaurants);

        addrestaurant = findViewById(R.id.btn_rest_lorest_addrestaurant);
        restaurantlist = findViewById(R.id.rv_rest_lorest_list);
    }
}