package com.example.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

// This class represent a Street Food review writing
public class SfWriteReview extends AppCompatActivity {
    // Defining the views
    Button send;
    EditText date, experience;
    RatingBar stars;
    ImageView SfImage;
    TextView name;

    // The needed members to know what streetFood is reviewed and who reviews it.
    StreetFood streetFood;
    User user;

    // Storing the entered data later in these variables
    String enteredDate;
    String enteredExperience;
    float chosenRating;
    String keyOfStreetFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_write_review);

        // Getting the data from intent got from the previous activity
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        streetFood = i.getParcelableExtra("streetfood");

        // Linking the views to this code
        send = findViewById(R.id.btn_sf_wr_send);
        date = findViewById(R.id.et_sf_wr_date);
        experience = findViewById(R.id.et_sf_wr_experience);
        stars = findViewById(R.id.rb_sf_wr_stars);
        SfImage = findViewById(R.id.iv_sf_wr_image);
        name = findViewById(R.id.tv_sf_wr_placename);

        // setting up the views
        Picasso.get().load(streetFood.getPicURL()).into(SfImage);
        name.setText(streetFood.getName());

        // The send button executes by this OnClickListener
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredDate = date.getText().toString();
                enteredExperience = experience.getText().toString();
                chosenRating = stars.getRating();

                /* Checking 3 states:
                 * 1st: Is the date exactly 10 chars long? Are the 3rd and 5th chars dots?
                 * 2nd: Is the entered review text longer then 10 characters (not left empty and not just 1-2 words)
                 * 3rd: The default value of Rating bar is 0.0, if it is not larger then rating is missing
                 * ELSE, any other cases: execute the review upload */
                if (enteredDate.length() != 10 || enteredDate.charAt(2) != '.' || enteredDate.charAt(5) != '.') {
                    Toast.makeText(SfWriteReview.this, "Date must be DD.MM.YYYY format!", Toast.LENGTH_LONG).show();
                } else if (enteredExperience.length() < 10) {
                    Toast.makeText(SfWriteReview.this, "Please leave a 10 characters or longer review!", Toast.LENGTH_LONG).show();
                } else if (stars.getRating() == 0.0) {
                    Toast.makeText(SfWriteReview.this, "Please set up a rating!", Toast.LENGTH_LONG).show();
                } else {
                    uploadReview();
                    finish();
                }
            }
        });

    }

    // This method is executed to upload the review after the proper statements satisfied above
    public void uploadReview() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("streetfoods");
        Query query = dbRef.orderByChild("name").equalTo(streetFood.getName()).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    keyOfStreetFood = ds.getKey();
                    DatabaseReference newDbRef = FirebaseDatabase.getInstance().getReference("streetfoods/" + keyOfStreetFood + "/reviews/");
                    ReviewTemplate review = new ReviewTemplate(user.getUsername(), enteredDate, enteredExperience, chosenRating);
                    newDbRef.child(newDbRef.push().getKey()).setValue(review);
                    Intent i = new Intent(SfWriteReview.this, SfListOfReviews.class);
                    i.putExtra("user", user);
                    i.putExtra("streetfood", streetFood);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}