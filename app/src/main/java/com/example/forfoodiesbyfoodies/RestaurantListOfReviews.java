package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// This class manages the ability of the app to list the reviews that a restaurant got from Food Critics
public class RestaurantListOfReviews extends AppCompatActivity implements RestaurantListOfReviewsCard.RestaurantReviewHolder.OnCardClickListener {

    // Defining the views and other object to be handled later
    Button addReview;
    RecyclerView listOfReviews;
    Restaurant restaurant;
    User user;
    ArrayList<ReviewTemplate> list = new ArrayList<>();
    DatabaseReference dbref;
    RestaurantListOfReviewsCard adapter;


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

        listOfReviews.setLayoutManager(new LinearLayoutManager(RestaurantListOfReviews.this));
        dbref = FirebaseDatabase.getInstance().getReference("restaurants");
        Query queryReviewsRef = dbref.orderByChild("picURL").equalTo(restaurant.getPicURL()).limitToFirst(1);
        queryReviewsRef.addListenerForSingleValueEvent(listener);

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
                    startActivity(k);
                }
            });
        } else {
            addReview.setVisibility(View.INVISIBLE);
        }


    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String key = "";
            for (DataSnapshot dss : snapshot.getChildren()) {
                key = dss.getKey();
            }
            DatabaseReference refToReviews = FirebaseDatabase.getInstance().getReference("restaurants/" + key + "/reviews/");
            refToReviews.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ReviewTemplate rt = new ReviewTemplate(
                                ds.child("writer").getValue().toString(),
                                ds.child("dateOfVisit").getValue().toString(),
                                ds.child("review").getValue().toString(),
                                Float.parseFloat(ds.child("rating").getValue().toString())
                        );
                        list.add(rt);
                    }
                    adapter = new RestaurantListOfReviewsCard(list, RestaurantListOfReviews.this);
                    listOfReviews.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
                /*ReviewTemplate rt = dss.getValue(ReviewTemplate.class);
                list.add(rt);
            }

            adapter = new RestaurantListOfReviewsCard(list, RestaurantListOfReviews.this);
            listOfReviews.setAdapter(adapter);
        }*/

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onCardClick(int i) {
        Intent intent = new Intent(RestaurantListOfReviews.this, RestaurantListOfReviews.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}