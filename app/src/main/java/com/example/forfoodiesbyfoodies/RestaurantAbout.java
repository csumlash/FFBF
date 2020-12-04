package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RestaurantAbout extends AppCompatActivity {

    // Definition of views
    ImageView image;
    TextView name, numbers, type, address, about;
    RatingBar stars;
    Button book, viewReviews;
    DatabaseReference dbref;
    StorageReference sref;
    String link;

    // A User type object to store the User object got from the previous activities.
    User user;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_about);

        // Initialisation of views so linking them to this code to be ready for use
        image = findViewById(R.id.iv_rest_ar_image);
        name = findViewById(R.id.tv_rest_ar_restaurantname);
        type = findViewById(R.id.tv_rest_ar_type);
        book = findViewById(R.id.btn_rest_ar_book);
        viewReviews = findViewById(R.id.btn_rest_ar_viewreviews);
        address = findViewById(R.id.tv_rest_ar_address);
        about = findViewById(R.id.tv_rest_ar_info);

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        restaurant = i.getParcelableExtra("restaurant");

        // Initialisation of Realtime and Storage database references
        dbref = FirebaseDatabase.getInstance().getReference("restaurant");
        sref = FirebaseStorage.getInstance().getReference("restaurants");

        // Setting up the values in the views Defined in the class then Initialised upon activity onCreate()
        Picasso.get().load(restaurant.getPicURL()).into(image);
        name.setText(restaurant.getName());
        type.setText(restaurant.getType());
        address.setText(restaurant.getAddress() + ", " + restaurant.getArea() + ", " + restaurant.getCity() + ", " + restaurant.getPostcode());
        about.setText(restaurant.getAbout());

        // Setting up the Make Reservation button to open up the stored web link
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(restaurant.getLink()));
                startActivity(browserIntent);
            }
        });

        // Setting the View Reviews button to start the Activity that lists the the Restaurant got from Food Critics
        viewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent initialisation then putting the logged in user and currently viewed restaurant objects then starting the activity
                Intent i = new Intent(RestaurantAbout.this, RestaurantListOfReviews.class);
                i.putExtra("user", user);
                i.putExtra("restaurant", restaurant);
                startActivity(i);
            }
        });
    }

    // This method forces the back button to go back to the List of eatery
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RestaurantAbout.this, RestaurantListOfRestaurants.class);
        i.putExtra("user", user);
        startActivity(i);
        finish();
    }
}