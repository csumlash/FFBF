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

public class SfListOfReviews extends AppCompatActivity implements SFListOfReviewsCard.SFReviewHolder.OnCardClickListener {

    User user;
    StreetFood streetFood;

    Button addReview;
    RecyclerView listOfReviews;

    ArrayList<ReviewTemplate> list = new ArrayList<ReviewTemplate>();
    DatabaseReference dbref;
    SFListOfReviewsCard adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list_of_reviews);

        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        streetFood = i.getParcelableExtra("streetfood");

        addReview = findViewById(R.id.btn_sf_review_addreview);
        listOfReviews = findViewById(R.id.rv_sf_review_list);

        listOfReviews.setLayoutManager(new LinearLayoutManager(SfListOfReviews.this));
        dbref = FirebaseDatabase.getInstance().getReference("streetfood");
        dbref.addListenerForSingleValueEvent(listener);

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(SfListOfReviews.this, SfWriteReview.class);
                k.putExtra("user", user);
                k.putExtra("streetfood", streetFood);
                startActivity(k);
            }
        });
    }

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()) {
                    ReviewTemplate rt = dss.getValue(ReviewTemplate.class);
                    list.add(rt);
                }

                adapter = new SFListOfReviewsCard(list, SfListOfReviews.this);
                listOfReviews.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


    @Override
    public void onCardClick(int i) {
        Intent intent = new Intent(SfListOfReviews.this, Profile.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}