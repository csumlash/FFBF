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

/* This class handles the writing of reviews for a restaurant and to let it do so, the activity
 * needs to get the user details who writes the reviews and restaurant details that is reviewed */
public class RestaurantWriteReview extends AppCompatActivity {

    // Defining the class attributes to be able to link the views to the code
    Button send;
    EditText date, experience;
    RatingBar stars;
    ImageView restImage;
    TextView name;

    // These object type variables will support the initialisation of Restaurant and User object from Intent
    Restaurant restaurant;
    User user;

    // The entered values of a Review will be stored on Class level to be accessed from all the methods
    String enteredDate;
    String enteredExperience;
    float chosenRating;
    String keyOfRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_write_review);

        // Getting the Intent data (User and Restaurant objects)
        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        restaurant = i.getParcelableExtra("restaurant");

        // Linking the views of activity to this code
        send = findViewById(R.id.btn_rest_wr_send);
        date = findViewById(R.id.et_rest_wr_date);
        experience = findViewById(R.id.et_rest_wr_experience);
        stars = findViewById(R.id.rb_rest_wr_rate);
        restImage = findViewById(R.id.iv_rest_wr_image);
        name = findViewById(R.id.tv_rest_wr_restname);

        // Loading the picture of restaurant then setting the name of restaurant
        Picasso.get().load(restaurant.getPicURL()).into(restImage);
        name.setText(restaurant.getName());

        // Sending the review and if all the requirements (fields are filled with proper data) then uploading to Database
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting the entered values from fields
                enteredDate = date.getText().toString();
                enteredExperience = experience.getText().toString();
                chosenRating = stars.getRating();

                /* Checking 3 states:
                 * 1st: Is the date exactly 10 chars long? Are the 3rd and 5th chars dots? If not: warn the user
                 * 2nd: Is the entered review text longer then 10 characters (not left empty and not just 1-2 words)
                 * 3rd: The default value of Rating bar is 0.0, if it is not larger then rating is missing
                 * 4th: if the entered day is larger than 31 or less than 1 ==> Impossible, warning
                 * 5th: if the entered month is larger than 12 or less than 1 ==> Impossible, warning
                 * 6th: if the entered year is more than the current year of writing ==> Impossible, warning
                 * 7th: if the whole entered date is newer then the moment of writing ==> Impossible, warning
                 * ELSE, a last validation of entered date if it is valid then execute review upload or warning */
                if (enteredDate.length() != 10 || enteredDate.charAt(2) != '.' || enteredDate.charAt(5) != '.') {
                    Toast.makeText(RestaurantWriteReview.this, "Date must be DD.MM.YYYY format!", Toast.LENGTH_LONG).show();
                } else if (enteredExperience.length() <= 10) {
                    Toast.makeText(RestaurantWriteReview.this, "Please leave a 10 characters or longer review!", Toast.LENGTH_LONG).show();
                } else if (stars.getRating() == 0.0) {
                    Toast.makeText(RestaurantWriteReview.this, "Please set up a rating!", Toast.LENGTH_LONG).show();
                } else {
                    uploadReview();
                    finish();
                }
            }
        });

    }

    public void uploadReview() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("restaurants");
        Query query = dbRef.orderByChild("name").equalTo(restaurant.getName()).limitToFirst(1);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    keyOfRestaurant = ds.getKey();
                    DatabaseReference newDbRef = FirebaseDatabase.getInstance().getReference("restaurants/" + keyOfRestaurant + "/reviews/");
                    ReviewTemplate review = new ReviewTemplate(user.getUsername(), enteredDate, enteredExperience, chosenRating);
                    newDbRef.child(newDbRef.push().getKey()).setValue(review);
                    Intent i = new Intent(RestaurantWriteReview.this, RestaurantListOfReviews.class);
                    i.putExtra("user", user);
                    i.putExtra("restaurant", restaurant);
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