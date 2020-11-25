package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SfWriteReview extends AppCompatActivity {

    Button send;
    ImageView sfimage;
    TextView sfname, addressline;
    EditText date, experience;
    RatingBar stars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_write_review);



        send = findViewById(R.id.btn_sf_wr_send);
        sfimage = findViewById(R.id.iv_sf_wr_image);
        sfname = findViewById(R.id.tv_sf_wr_placename);
        addressline = findViewById(R.id.tv_sf_wr_addressline);
        date = findViewById(R.id.et_sf_wr_date);
        experience = findViewById(R.id.et_sf_wr_experience);
        stars = findViewById(R.id.rb_sf_wr_stars);
    }
}