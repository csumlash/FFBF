package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantListOfRestaurants extends AppCompatActivity implements RestaurantListOfRestaurantsCard.RestaurantHolder.OnCardClickListener {

    Button add;
    RecyclerView restList;
    ArrayList<Restaurant> list = new ArrayList<>();
    DatabaseReference dbref;
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

        add = findViewById(R.id.btn_rest_lorest_addrestaurant);
        restList = findViewById(R.id.rv_rest_lorest_list);
        restList.setLayoutManager(new LinearLayoutManager(RestaurantListOfRestaurants.this));
        dbref = FirebaseDatabase.getInstance().getReference("restaurants");
        dbref.addListenerForSingleValueEvent(listener);

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
        }else {
            add.setVisibility(View.INVISIBLE);
        }
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss : snapshot.getChildren()){
                Restaurant r = dss.getValue(Restaurant.class);
                list.add(r);
            }

            adapter = new RestaurantListOfRestaurantsCard(list, RestaurantListOfRestaurants.this);
            restList.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onCardClick(int i) {
            Intent intent = new Intent(RestaurantListOfRestaurants.this, RestaurantAbout.class);
            intent.putExtra("user", user);
            intent.putExtra("restaurant", list.get(i));
            startActivity(intent);
    }
}