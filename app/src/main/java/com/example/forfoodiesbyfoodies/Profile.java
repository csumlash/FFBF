package com.example.forfoodiesbyfoodies;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Profile extends AppCompatActivity {

    Button editProfile, promoteToCritic;
    ImageView profPic;
    TextView firstName, lastName, email, psw;
    RatingBar ratingBar;
    RecyclerView reviewsOfFC;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //editProfile = findViewById(R.id.btn_profile_editProfile);
        profPic = findViewById(R.id.iv_profile_paimage);
        firstName = findViewById(R.id.tv_profile_firtname);
        lastName = findViewById(R.id.tv_profile_lastname);
        email = findViewById(R.id.tv_profile_email);
        psw = findViewById(R.id.tv_profile_password);
        promoteToCritic = findViewById(R.id.btn_profile_promote);
        ratingBar = findViewById(R.id.rb_profile_stars);
        reviewsOfFC = findViewById(R.id.rv_profile_reviews);

    }
}