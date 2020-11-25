package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class RestaurantWriteReview extends AppCompatActivity {

    Button send;
    EditText date, experience;
    RatingBar stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_write_review);


        send = findViewById(R.id.btn_rest_wr_send);
        date = findViewById(R.id.et_rest_wr_date);
        experience = findViewById(R.id.et_rest_wr_experience);
        stars = findViewById(R.id.rb_rest_wr_rate);
    }
}