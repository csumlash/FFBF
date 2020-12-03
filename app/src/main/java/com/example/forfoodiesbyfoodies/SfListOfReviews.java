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

        /* If the user that opens this activity is a food critic then show the Write Review button
         * and then setting up a listener for the button to open the Review Writing activity
         * or hid the button elsewhere */
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


    }

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
                        ReviewTemplate rt = new ReviewTemplate(
                                ds.child("writer").getValue().toString(),
                                ds.child("dateOfVisit").getValue().toString(),
                                ds.child("review").getValue().toString(),
                                Float.parseFloat(ds.child("rating").getValue().toString())
                        );
                        list.add(rt);
                    }
                    adapter = new SfListOfReviewsCard(list, SfListOfReviews.this);
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
    public void onCardClick(int i) {
        Intent intent = new Intent(SfListOfReviews.this, Profile.class);
        intent.putExtra("user", user);
        intent.putExtra("username", list.get(i).getWriter());
        startActivity(intent);
    }
}