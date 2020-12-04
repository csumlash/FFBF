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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SfListOfReviews extends AppCompatActivity implements SfListOfReviewsCard.SfReviewHolder.OnCardClickListener {

    // Defining the views and other object to be handled later
    Button addReview;
    RecyclerView listOfReviews;
    StreetFood streetfood;
    User user;
    ArrayList<ReviewTemplate> list = new ArrayList<>();
    DatabaseReference dbref;
    SfListOfReviewsCard adapter;

    // setting up a listener, to get the key of a Street food place of which reviews are requested
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String key = "";
            for (DataSnapshot dss : snapshot.getChildren()) {
                key = dss.getKey();
            }
            DatabaseReference refToReviews = FirebaseDatabase.getInstance().getReference("streetfoods/" + key + "/reviews/");
            refToReviews.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        // Because of the float member of Object a manual object initialisation is called
                        ReviewTemplate rt = new ReviewTemplate(
                                ds.child("writer").getValue().toString(),
                                ds.child("dateOfVisit").getValue().toString(),
                                ds.child("review").getValue().toString(),
                                Float.parseFloat(ds.child("rating").getValue().toString())
                        );
                        rt.setReviewKey(ds.getKey());
                        list.add(rt);
                    }
                    // Initialisation of adapter then setting up the list
                    adapter = new SfListOfReviewsCard(list, SfListOfReviews.this, user, streetfood);
                    listOfReviews.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list_of_reviews);

        // Getting the intent data from caller activity (initialising user and streetfood object)
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        streetfood = i.getParcelableExtra("streetfood");

        // Linking the views to this code to be used later
        listOfReviews = findViewById(R.id.rv_sf_review_list);
        addReview = findViewById(R.id.btn_sf_review_addreview);

        listOfReviews.setLayoutManager(new LinearLayoutManager(SfListOfReviews.this));

        dbref = FirebaseDatabase.getInstance().getReference("streetfoods");
        Query queryReviewsRef = dbref.orderByChild("picURL").equalTo(streetfood.getPicURL()).limitToFirst(1);
        queryReviewsRef.addListenerForSingleValueEvent(listener);

        /* Only users can write reviews */
        if (user.getUserType().equals("user")) {
            addReview.setVisibility(View.VISIBLE);
            addReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent k = new Intent(SfListOfReviews.this, SfWriteReview.class);
                    k.putExtra("streetfood", streetfood);
                    k.putExtra("user", user);
                    startActivity(k);
                }
            });
        } else {
            addReview.setVisibility(View.INVISIBLE);
        }
    }

    // Overriding the back button of the phone to destroy all the previous activity to avoid conflicts of uploads/requests
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SfListOfReviews.this, SfAbout.class);
        i.putExtra("user", user);
        i.putExtra("streetfood", streetfood);
        startActivity(i);
        finish();
    }

    // Card element on click action
    @Override
    public void onCardClick(int i) {
        Intent intent = new Intent(SfListOfReviews.this, Profile.class);
        intent.putExtra("user", user);
        intent.putExtra("username", list.get(i).getWriter());
        startActivity(intent);
    }
}