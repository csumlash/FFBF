package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SfAbout extends AppCompatActivity {

    TextView title, vegan, name, fullAddress, description;
    ImageView image;
    Button addReview;
    RecyclerView reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_about);

        /* first part: type of view (tv, iv, btn)
        ** seconds part: asf = abbreviation of class name
        ** third part: name of field (title, image, vegan...) */
        title       = findViewById(R.id.tv_sf_list_title);
        image       = findViewById(R.id.iv_sf_list_image);
        vegan       = findViewById(R.id.tv_sf_list_vegan);
        name        = findViewById(R.id.tv_sf_list_name);
        fullAddress = findViewById(R.id.tv_sf_list_address);
        description = findViewById(R.id.tv_sf_list_description);
        addReview   = findViewById(R.id.btn_sf_list_review);
        reviews     = findViewById(R.id.rv_sf_list_reviews);
    }
}