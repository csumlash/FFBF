package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class RestaurantAbout extends AppCompatActivity {

    ImageView image;
    TextView name, numbers, type, address,about;
    RatingBar stars;
    Button book, view;

    // A User type object to store the User object got from the previous activities.
    User user;

    Restaurant r;

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

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        r = i.getParcelableExtra("restaurant");

        Picasso.get().load(r.getPicURL()).fit().into(image);
        name.setText(r.getName());
        //stars.setNumStars();
        type.setText(r.getType());
        address.setText(r.getAddress() + "," + r.getCity() + "," + r.getPostcode() + "," + r.getArea());
        about.setText(r.getAbout());

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.opentable.com/s/?covers=2&dateTime=2019-02-25%2019%3A00&metroId=72&regionIds=5316&pinnedRids%5B0%5D=87967&enableSimpleCuisines=true&includeTicketedAvailability=true&pageType=0 ";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantAbout.this, RestaurantListOfReviews.class);
                i.putExtra("user", user);
                i.putExtra("restaurant", r);
                startActivity(i);
            }
        });
    }
}