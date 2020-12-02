package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SfListOfReviews extends AppCompatActivity {

    User user;
    StreetFood streetFood;

    Button addReview;
    RecyclerView listOfReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list_of_reviews);

        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        streetFood = i.getParcelableExtra("streetfood");

        addReview = findViewById(R.id.btn_sf_review_addreview);
        listOfReviews = findViewById(R.id.rv_sf_review_list);

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
}