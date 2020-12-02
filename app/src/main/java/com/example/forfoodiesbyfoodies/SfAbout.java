package com.example.forfoodiesbyfoodies;

import android.content.Intent;
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

public class SfAbout extends AppCompatActivity {

    TextView title, vegie, name, address, about;
    ImageView image;
    Button viewReview;
    RatingBar stars;
    DatabaseReference dbref;
    StorageReference sref;


    // A User type object to store the User object got from the previous activities.
    User user;
    StreetFood sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_about);

        /* first part: type of view (tv, iv, btn)
         * seconds part: asf = abbreviation of class name
         * third part: name of field (title, image, vegan...) */
        title = findViewById(R.id.tv_sf_list_title);
        image = findViewById(R.id.iv_sf_list_image);
        vegie = findViewById(R.id.tv_sf_list_vegan);
        stars = findViewById(R.id.rb_sf_about_stars);
        name = findViewById(R.id.tv_sf_list_name);
        address = findViewById(R.id.tv_sf_about_address);
        about = findViewById(R.id.tv_sf_about_description);
        viewReview = findViewById(R.id.btn_sf_list_viewreviews);

        // Initialisation of Realtime and Storage database references
        dbref = FirebaseDatabase.getInstance().getReference("streetfood");
        sref = FirebaseStorage.getInstance().getReference("streetfood");

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        sf = i.getParcelableExtra("streetfood");

        Picasso.get().load(sf.getPicURL()).into(image);
        name.setText(sf.getName());
        stars.setRating(getRating());
        address.setText(sf.getAddress() + ", " + sf.getCity() + ", " + sf.getPostcode() + ", " + sf.getArea());
        about.setText(sf.getAbout());

        if (sf.getIsVeganFriendly().equals("no")) {
            vegie.setVisibility(View.INVISIBLE);
        } else {
            vegie.setText("Vegan food available!");
        }

        //stars.setRating(getRating());

        viewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SfAbout.this, SfReviewList.class);
                i.putExtra("user", user);
                i.putExtra("streetfood", sf);
                startActivity(i);
            }
        });
    }

    public int getRating() {
        return 3;
    }
}