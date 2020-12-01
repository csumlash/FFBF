package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class RestaurantListOfReviews extends AppCompatActivity {

    Button addReview;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_of_reviews);

        addReview = findViewById(R.id.btn_rest_loreviews_addnewreview);
        list = findViewById(R.id.rv_rest_loreviews_list);
    }
}