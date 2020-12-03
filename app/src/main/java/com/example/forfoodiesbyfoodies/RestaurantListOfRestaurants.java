package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RestaurantListOfRestaurants extends AppCompatActivity implements RestaurantListOfRestaurantsCard.RestaurantHolder.OnCardClickListener {

    // Definition of views to be ready for initialisation
    Button add;
    RecyclerView restList;
    ArrayList<Restaurant> list = new ArrayList<>();
    DatabaseReference dbRef;
    RestaurantListOfRestaurantsCard adapter;

    // A User type object to store the User object got from the previous activities.
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_of_restaurants);

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        // Linking and so initialising the views to be usable in this code
        add = findViewById(R.id.btn_rest_lorest_addrestaurant);
        restList = findViewById(R.id.rv_rest_lorest_list);
        restList.setLayoutManager(new LinearLayoutManager(RestaurantListOfRestaurants.this));

        // If the user logged in is admin then show user search field, button and warning message
        if (user.getUserType().equals("admin")) {
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(RestaurantListOfRestaurants.this, RestaurantAddNewRestaurant.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            });
        } else {
            add.setVisibility(View.INVISIBLE);
        }

        dbRef = FirebaseDatabase.getInstance().getReference("restaurants");
        dbRef.addListenerForSingleValueEvent(listener);

    }

    // The following listener is called to request Restaurants from the DB then filling up the Restaurant type list of Restaurants
    ValueEventListener listener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss : snapshot.getChildren()) {
                Restaurant r = dss.getValue(Restaurant.class);
                list.add(r);
            }

            //Sorting the list alphabetically by Restaurant name
            Collections.sort(list, new Comparator<Restaurant>() {
                @Override
                public int compare(Restaurant o1, Restaurant o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            // This adapter will contain the Initialised CardViews (to let the restaurants be handle 1-by-1 by the CardViews)
            adapter = new RestaurantListOfRestaurantsCard(list, RestaurantListOfRestaurants.this);
            // Setting up the RecyclerVies adapter with the previously defined Card object
            restList.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    // This method handles the event of clicking on one Card of the RecyclerView
    @Override
    public void onCardClick(int i) {
        Intent intent = new Intent(RestaurantListOfRestaurants.this, RestaurantAbout.class);
        intent.putExtra("user", user);
        intent.putExtra("restaurant", list.get(i));
        startActivity(intent);
    }
}