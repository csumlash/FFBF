package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantAbout extends AppCompatActivity {

    ImageView restArImage;
    TextView restArRestaurantName, restArReviewNumbers, restArPrice, restArType, restArAddress, restArInfo;
    RatingBar restArStars;
    Button restArBook, restArViewReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_about);

        restArImage = findViewById(R.id.iv_rest_ar_image);
        restArRestaurantName = findViewById(R.id.tv_rest_ar_restaurantname);
        restArStars = findViewById(R.id.rb_rest_ar_stars);
        restArReviewNumbers = findViewById(R.id.tv_rest_ar_reviewnumbers);
        restArPrice = findViewById(R.id.tv_rest_ar_price);
        restArType = findViewById(R.id.tv_rest_ar_type);
        restArBook = findViewById(R.id.btn_rest_ar_book);
        restArViewReviews = findViewById(R.id.btn_rest_ar_viewreviews);
        restArAddress = findViewById(R.id.tv_rest_ar_address);
        restArInfo = findViewById(R.id.tv_rest_ar_info);
    }
}