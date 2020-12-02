package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

// This class manages the ability of the app to list the reviews that a restaurant got from Food Critics
public class RestaurantListOfReviews extends AppCompatActivity {

    // Defining the views and other object to be handled later
    Button addReview;
    RecyclerView listOfReviews;
    Restaurant restaurant;
    ImageButton delete;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_of_reviews);

        // Getting the intent data from caller activity (initialising user and restaurant object)
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        restaurant = i.getParcelableExtra("restaurant");

        // Linking the views to this code to be used later
        listOfReviews = findViewById(R.id.rv_rest_loreviews_list);
        addReview = findViewById(R.id.btn_rest_loreviews_addnewreview);
        delete = findViewById(R.id.ib_sf_review_card_delete);

        /* If the user that opens this activity is a food critic then show the Write Review button
         * and then setting up a listener for the button to open the Review Writing activity
         * or hid the button elsewhere */
        if (user.getUserType().equals("foodcritic")) {
            addReview.setVisibility(View.VISIBLE);
            addReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent k = new Intent(RestaurantListOfReviews.this, RestaurantWriteReview.class);
                    k.putExtra("restaurant", restaurant);
                    k.putExtra("user", user);
                }
            });
        } else {
            addReview.setVisibility(View.INVISIBLE);
        }


    }
}