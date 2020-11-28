package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantAbout extends AppCompatActivity {

    ImageView image;
    TextView name, numbers, type, address,about;
    RatingBar stars;
    Button book, view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_about);

        image = findViewById(R.id.iv_rest_ar_image);
        name = findViewById(R.id.tv_rest_ar_restaurantname);
        stars = findViewById(R.id.rb_rest_ar_stars);
        numbers = findViewById(R.id.tv_rest_ar_reviewnumbers);
        type = findViewById(R.id.tv_rest_ar_type);
        book = findViewById(R.id.btn_rest_ar_book);
        view = findViewById(R.id.btn_rest_ar_viewreviews);
        address = findViewById(R.id.tv_rest_ar_address);
        about = findViewById(R.id.tv_rest_ar_info);

    Restaurant r = getIntent().getParcelableExtra("RESTAURANT");

    name.setText(r.getName());

    }
}