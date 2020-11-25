package com.example.forfoodiesbyfoodies;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Profile extends AppCompatActivity {

    Button editProfile, uploadImg, promoteToCritic;
    ImageView profPic;
    TextView firstName, lastName, email, psw, foodCritText;
    RatingBar ratingBar;
    RecyclerView reviewsOfFC;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile = findViewById(R.id.btn_profile_editprofile);
        profPic = findViewById(R.id.iv_profile_paimage);
        uploadImg = findViewById(R.id.btn_profile_upload);
        firstName = findViewById(R.id.tv_profile_firtname);
        lastName = findViewById(R.id.tv_profile_lastname);
        email = findViewById(R.id.tv_profile_email);
        psw = findViewById(R.id.tv_profile_password);
        promoteToCritic = findViewById(R.id.btn_profile_promote);
        foodCritText = findViewById(R.id.tv_profile_foodcritic);
        ratingBar = findViewById(R.id.rb_profile_stars);
        reviewsOfFC = findViewById(R.id.rv_profile_reviews);

    }
}