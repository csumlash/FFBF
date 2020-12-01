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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class RestaurantAddNewRestaurant extends AppCompatActivity {
    // Defining activity view types those will be initialised in the onCreate process
    ImageView image;
    Button send;
    EditText name, address, postcode, area, city, type, about, link;
    TextView warning;

    // The following object typed variables will be used to handle Firebase database/storage works
    DatabaseReference dbref;
    StorageReference sref;
    Uri image_path;

    // A User type object to store the User object got from the previous activities.
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_new_restaurant);

        // Linking the views of activity to this code (initialisation of variable)
        image = findViewById(R.id.iv_rest_anr_image);
        name = findViewById(R.id.et_rest_anr_nameofrestaurant);
        address = findViewById(R.id.et_rest_anr_addressline);
        postcode = findViewById(R.id.et_rest_anr_postcode);
        area = findViewById(R.id.et_rest_anr_areaname);
        city = findViewById(R.id.et_rest_anr_city);
        type = findViewById(R.id.et_rest_anr_typeofrest);
        about = findViewById(R.id.et_rest_anr_desciption);
        warning = findViewById(R.id.tv_rest_anr_warning);
        send = findViewById(R.id.btn_rest_anr_send);
        link = findViewById(R.id.et_rest_anr_link);

        // Getting direct references to restaurants node and directory of Firebase Realtime and Storage databases
        sref = FirebaseStorage.getInstance().getReference("restaurants");
        dbref = FirebaseDatabase.getInstance().getReference("restaurants");

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        /*Image View will respond to click to choose the picture.
         *Intent used like default constructor to enter the phone directory and choose a picture.
         *Declare what kind of files are valid to be used as a picture.
         *Bring the selected image to the app.
         *Given an ID for my request code to identify when this task will be finished.*/
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 105);
            }
        });

        // The listener of clicking on the SEND button button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting and storing the entered values of EditText fields
                String enteredName = name.getText().toString();
                String enteredAddress = address.getText().toString();
                String enteredArea = area.getText().toString();
                String enteredCity = city.getText().toString();
                String enteredPostcode = postcode.getText().toString();
                String enteredAbout = about.getText().toString();
                String enteredType = type.getText().toString();
                String enteredLink = link.getText().toString();

                /* The MINIMUM requirements of image and data upload processes are declared in this statement
                 *  - the image_path must be initialised ==> image must be already picked in the ImageView
                 *  - the entered values must be more characters longer by the listed > requirements */
                if (image_path != null && enteredName.length() > 0 && enteredAddress.length() > 0 &&
                        enteredArea.length() > 0 && enteredCity.length() > 0 && enteredPostcode.length() > 0 &&
                        enteredAbout.length() > 0 && enteredType.length() > 0) {

                    // Initialising and getting dbref unique ID to name the files
                    String id = dbref.push().getKey();

                    /* Images will get id after images are uploaded to firebase storage and database will use this id to save to restaurants.
                     * This method will return the extension of the image to name it.*/
                    final StorageReference reference = sref.child(id + "." + getExtension(image_path));
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
                                                    String url = uri.toString();
                                                    Restaurant restaurant = new Restaurant(url, enteredName, enteredAddress, enteredArea, enteredCity, enteredPostcode, enteredAbout, enteredType, enteredLink);
                                                    dbref.child(id).setValue(restaurant)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    /*Intent will navigate to an other activity.
                                                                     * Declared parameter is the content and the java class of the activity where will be navigate.*/
                                                                    Intent i = new Intent(RestaurantAddNewRestaurant.this, RestaurantListOfRestaurants.class);
                                                                    i.putExtra("user", user);
                                                                    startActivity(i);
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {

                                                                }
                                                            });
                                                }
                                            })
                                            // If cannot get the link of the uploaded image to the storage then warning the user
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    //If fail to get the URL of the image, it necessary to delete that kind of orphan image from the database, because no one can access to it and just it takes place.
                                                    reference.delete();
                                                    Toast.makeText(RestaurantAddNewRestaurant.this, "Internet disruption", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            })
                            // If the whole upload process fails then warning the user of network issues
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RestaurantAddNewRestaurant.this, "Internet disruption, try again please", Toast.LENGTH_LONG).show();
                                }
                            });
                    // if the minimum requirements of data and image upload is not satisfied then warning the user
                } else {
                    warning.setVisibility(View.VISIBLE);
                    warning.setTextColor(getResources().getColor(R.color.red));
                }
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
        if (requestCode == 105 && resultCode == RESULT_OK && data.getData() != null) {
            Picasso.get().load(data.getData()).fit().into(image);
            image_path = data.getData();
        }
    }

    /* Here the code is getting the file extension from the file path used during image upload */
    private String getExtension(Uri path) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(path));
    }
}