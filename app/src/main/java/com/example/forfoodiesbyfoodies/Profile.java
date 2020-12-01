package com.example.forfoodiesbyfoodies;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/* This class is handling user profile presentation that can be:
 *  - own profile inspection directly by the VIEW MY PROFILE button on dashboard
 *  - a requested another user profile with putExtra("username","emailaddress"); */
public class Profile extends AppCompatActivity {

    // Defining activity views
    Button promoteToCritic, pswButton;
    ImageView profPic;
    TextView fullName, email, foodCritText, reviewsTitle, activityTitle;
    EditText pswOld, pswNew;
    RatingBar ratingBar;
    RecyclerView reviewsOfFC;
    // Support User object typed variables to handle own or another users data
    User user;
    User anotherUser;
    Uri image_path;
    // Support variable to handle other user profile views and queries
    String username;
    // Support variable to access to the Firebase Realtime and Storage databases
    DatabaseReference dbRef;
    StorageReference sRef;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Getting data from intent
        i = getIntent();
        user = i.getParcelableExtra("user");
        anotherUser = i.getParcelableExtra("anotherUser");
        username = i.getStringExtra("username");

        // Initialisation of Realtime and Storage database references
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        sRef = FirebaseStorage.getInstance().getReference("users");

        // Linking views to this code
        activityTitle = findViewById(R.id.tv_profile_title);
        profPic = findViewById(R.id.iv_profile_paimage);
        fullName = findViewById(R.id.tv_profile_fullName);
        email = findViewById(R.id.tv_profile_email);
        pswOld = findViewById(R.id.et_profile_old_pass);
        pswNew = findViewById(R.id.et_profile_new_pass);
        pswButton = findViewById(R.id.btn_profile_set_new_pass);
        promoteToCritic = findViewById(R.id.btn_profile_promote);
        foodCritText = findViewById(R.id.tv_profile_foodcritic);
        ratingBar = findViewById(R.id.rb_profile_stars);
        reviewsOfFC = findViewById(R.id.rv_profile_reviews);
        reviewsTitle = findViewById(R.id.tv_profile_reviews);

        /* If there is a username request (that is different from the logged in user) and
         * there is no loaded another user details in object
         * then launch query and reload activity with given "anotherUser" object details */
        if (username != null && anotherUser == null && !username.equals(user.getUsername())) {
            /* Calling the method that queries the user details from database
             * This method creates anotherUser object that will be used at the "else" case */
            queryUser(username);
        } else if (anotherUser == null || anotherUser.getUsername().equals(user.getUsername())) {
            if (user.getPicUrl() != null) {
                Picasso.get().load(user.getPicUrl()).into(profPic);
            } else {
                profPic.setImageResource(R.drawable.ic_baseline_add_photo_image);
            }
            // Setting up the texts in the views from the logged in user object data
            fullName.setText(user.getFirstName() + " " + user.getLastName());
            email.setText(user.getUsername());
            // The following methods and listeners are called and set when the user is visiting their own profile
            profPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent definition before starting the image picker
                    Intent k = new Intent();
                    // Setting up what kind of files/content is acceptable
                    k.setType("image/*");
                    // Setting up what Android System built-in file navigator is needed
                    k.setAction(Intent.ACTION_GET_CONTENT);
                    // Starting the system file navigator with the given image requirements
                    startActivityForResult(k, 111);
                    //onPause();

                }
            });


            // Updating user password
            pswButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePassword();
                }
            });
        } else {
            if (anotherUser.getPicUrl() != null) {
                Picasso.get().load(anotherUser.getPicUrl()).into(profPic);
            } else {
                profPic.setImageResource(R.drawable.ic_baseline_add_photo_image);
            }

            // Show user's full name (build from first and last names) and email
            fullName.setText(anotherUser.getFirstName() + " " + anotherUser.getLastName());
            email.setText(anotherUser.getUsername());

            // If the visited profile is a User by an admin then show Promotion to Food Critic option
            if (anotherUser.getUserType().equals("user") && user.getUserType().equals("admin")) {
                promoteToCritic.setVisibility(View.VISIBLE);
                promoteToCritic.setText("Promote to Food Critic");
                promoteToCritic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        promoteOrDemoteFoodCritic(true);
                        Intent changedUser = new Intent(Profile.this, Dashboard.class);
                        changedUser.putExtra("user", user);
                        changedUser.putExtra("anotherUser", anotherUser);
                        startActivity(changedUser);
                        finish();
                    }

                });
            }

            // If the visited profile is a Food Ciritc by an admin then show Demotion to User option
            if (anotherUser.getUserType().equals("foodcritic") && user.getUserType().equals("admin")) {
                promoteToCritic.setText("Demote to user");
                promoteToCritic.setVisibility(View.VISIBLE);
                promoteToCritic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        promoteOrDemoteFoodCritic(false);
                        Intent changedUser = new Intent(Profile.this, Dashboard.class);
                        changedUser.putExtra("user", user);
                        changedUser.putExtra("anotherUser", anotherUser);
                        startActivity(changedUser);
                        finish();
                    }
                });
            }

            // If anybody is visiting a Food Critic's profile then show rating of their critics, list of critics, etc
            if (anotherUser.getUserType() == "foodcritic") {
                foodCritText.setVisibility(View.VISIBLE);
                reviewsTitle.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
                reviewsOfFC.setVisibility(View.VISIBLE);
            }

            /* Every other cases when someone visits others' profile
             * - change activity title from My Profile to View user's profile
             * - hide edit profile button & password field except if the user is viewing himself*/
            activityTitle.setText("View user's profile");
            pswOld.setVisibility(View.INVISIBLE);
            pswNew.setVisibility(View.INVISIBLE);
            pswButton.setVisibility(View.INVISIBLE);
        }
    }


    /* This method is responsible to:
     *  - Query the user's firstName, lastName and userType (user, foodcrit, admin) of requested user profile
     *  - Build a User type object from query to store all the user details and add it to the intent (Profile) starter */
    public void queryUser(String username) {
        /* A simple query that has
         *  - orderByChild("username") => to sort/order the query by the username (email) values
         *  - equalTo(username) => requests only equal values to username got as method call parameter
         *  - limitToFirst(1) => the result will contain 1 record even there were more results in the query */
        Query usersQuery = dbRef.orderByChild("username").equalTo(username).limitToFirst(1);
        // Executing the query
        usersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Fetching data of query then inserting and so creating User object
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // The data gathering from snapshot of query
                    String username = ds.child("username").getValue().toString();
                    String firstName = ds.child("firstName").getValue().toString();
                    String lastName = ds.child("lastName").getValue().toString();
                    String userType = ds.child("userType").getValue().toString();
                    String profPicUrl;
                    try {
                        profPicUrl = ds.child("url").getValue().toString();
                    } catch (Exception e) {
                        profPicUrl = null;
                    }
                    // Creating the object with the fetched data
                    User anotherUser = new User(username, firstName, lastName, userType, profPicUrl);
                    // Creating and filling intent with extra data to recall the Profile itself by itself
                    Intent k = new Intent(Profile.this, Profile.class);
                    k.putExtra("user", user);
                    k.putExtra("anotherUser", anotherUser);
                    startActivity(k);
                    finish();
                }
            }

            // If a query cannot be executed because of any network issue then warning the user
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Network issue, try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    /* This method is in relation with the "startActivityForResult() method called in the image
     *  picker solution and this method is responsible to commit the image open from the device,
     *  to show it in the ImageView view and to initialise the image_path variable with the
     *  value of path (location) of the image on the device */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data.getData() != null) {
            Picasso.get().load(data.getData()).into(profPic);
            image_path = data.getData();
            picUploader();
        }
    }

    // This method is called by the onActivityResult method to upload the chosen profile picture from the device
    private void picUploader() {
        // Requesting a random unique ID from the Realtime Database key generator
        String id = dbRef.push().getKey();
        // Setting up the file name with the unique ide then a dot as name and file extension separator then applying the file type/extension
        final StorageReference reference = sRef.child(id + "." + getExtension(image_path));
        /* The next part is the upload of image and check if that was successfully uploaded or failed.*/
        reference.putFile(image_path)
                // What to do if the upload process was successful
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl()
                                /* The process is not going to go to the next activity without the URL of the image, only if the method is successful.
                                 * - but if successful then storing the database link of image to be able to store it in the Realtime DB
                                 * - and uploading all the entered data with the direct url link to the Realtime database */
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // If the user had a previous profile picture then deleting it to avoid orphan pictures in storage
                                        if (user.getPicUrl() != null) {
                                            StorageReference previousPicture = FirebaseStorage.getInstance().getReferenceFromUrl(user.getPicUrl());
                                            previousPicture.delete();
                                        }
                                        // Getting the URL
                                        String url = uri.toString();
                                        // Calling the method with url parameter to update the user's details in the Realtime Database
                                        pushProfPicLink(url);
                                    }
                                })
                                // If cannot get the link of the uploaded image to the storage then warning the user
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //If fail to get the URL of the image, it necessary to delete that kind of orphan image from the database, because no one can access to it and just it takes place.
                                        reference.delete();
                                        Toast.makeText(Profile.this, "Internet disruption, please try again", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                })
                // If the whole upload process fails then warning the user of network issues
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, "Internet disruption, please try again", Toast.LENGTH_LONG).show();
                    }
                });

    }

    // This method is called by the picUploader() method always to update the user's current profile picture data
    public void pushProfPicLink(String url) {
        Query usersQuery = dbRef.orderByChild("username").equalTo(user.getUsername()).limitToFirst(1);
        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot theUser : snapshot.getChildren()) {
                    dbRef.child(theUser.getKey()).child("url").setValue(url);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(Profile.this, "Profile picture is updated successfully", Toast.LENGTH_SHORT).show();
    }

    /* Here the code is getting the file extension from the file path used during image upload */
    private String getExtension(Uri path) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(path));
    }

    public void updatePassword() {
        // Creating connection to the Authentication database
        FirebaseUser userToUpdate = FirebaseAuth.getInstance().getCurrentUser();
        if (pswOld.getText() != null && pswOld.getText().toString().length() > 0 &&
                pswNew.getText() != null && pswNew.getText().toString().length() > 0) {
            // Reauthorise the user by the saved username and typed old password if old and new psw are provided
            AuthCredential credentials = EmailAuthProvider
                    .getCredential(user.getUsername(), pswOld.getText().toString());
            // Launching the reauthorisation process with the username and password
            userToUpdate.reauthenticate(credentials)
                    // If reauthorisation ended successfully
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // If the authorisation ended successfully
                            if (task.isSuccessful()) {
                                // then trying to update the password
                                userToUpdate.updatePassword(pswNew.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // if the update process was successful then inform the user
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Profile.this, "Password is updated", Toast.LENGTH_LONG).show();
                                        } else {
                                            // if process failed then inform the user of network issue
                                            Toast.makeText(Profile.this, "Check your network", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                // If the authorisation failed then asking to check the old/previous password
                                Toast.makeText(Profile.this, "Your old password is incorrect", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(Profile.this, "Need both old and new passwords", Toast.LENGTH_LONG).show();
        }

    }

    private void promoteOrDemoteFoodCritic(boolean promote) {
        Query usersQuery = dbRef.orderByChild("username").equalTo(anotherUser.getUsername()).limitToFirst(1);
        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot theUser : snapshot.getChildren()) {
                    if (promote) {
                        dbRef.child(theUser.getKey()).child("userType").setValue("foodcritic");
                        anotherUser.setUserType("foodcritic");
                    } else {
                        dbRef.child(theUser.getKey()).child("userType").setValue("user");
                        anotherUser.setUserType("user");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        finish();
    }
}