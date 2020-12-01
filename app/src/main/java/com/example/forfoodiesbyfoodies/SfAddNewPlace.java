package com.example.forfoodiesbyfoodies;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
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

public class SfAddNewPlace extends AppCompatActivity {

    Button send;
    ImageView sfpic;
    TextView warning;
    EditText name, address, postcode, area, city, about;
    String vegie;

    // The following object typed variables will be used to handle Firebase database/storage works
    DatabaseReference dbref;
    StorageReference sref;
    Uri image_path;

    // A User type object to store the User object got from the previous activities.
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_add_new_place);

        send = findViewById(R.id.btn_sf_anp_send);
        sfpic = findViewById(R.id.iv_sf_anp_image);
        warning = findViewById(R.id.tv_sf_anp_warning);
        name = findViewById(R.id.et_sf_anp_nameofsf);
        address = findViewById(R.id.et_sf_anp_addressline);
        postcode = findViewById(R.id.et_sf_anp_postcode);
        area = findViewById(R.id.et_sf_anp_areaname);
        city = findViewById(R.id.et_sf_anp_city);
        about = findViewById(R.id.et_sf_anp_about);
        Switch sw = (Switch) findViewById(R.id.switch_sf_anp_vegie);

        // Getting direct references to street food node and directory of Firebase Realtime and Storage databases
        sref = FirebaseStorage.getInstance().getReference("streetfood");
        dbref = FirebaseDatabase.getInstance().getReference("streetfood");

        // Getting the User object from intent passed from previous activities
        Intent i = getIntent();
        user = i.getParcelableExtra("user");



        /*Image View will respond to click to choose the picture.
         *Intent used like default constructor to enter the phone directory and choose a picture.
         *Declare what kind of files are valid to be used as a picture.
         *Bring the selected image to the app.
         *Given an ID for my request code to identify when this task will be finished.*/
        sfpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 104);
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

                 sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (sw.isChecked())
                            vegie = sw.getTextOn().toString();
                        }
                    });


                /* The MINIMUM requirements of image and data upload processes are declared in this statement
                 *  - the image_path must be initialised ==> image must be already picked in the ImageView
                 *  - the entered values must be more characters longer by the listed > requirements */
                if (image_path != null && enteredName.length() > 0 && enteredAddress.length() > 0 &&
                        enteredArea.length() > 0 && enteredCity.length() > 0 && enteredPostcode.length() > 0 &&
                        enteredAbout.length() > 0) {

                    // Initialising and getting dbref unique ID to name the files
                    String id = dbref.push().getKey();

                    /* Images will get id after images are uploaded to firebase storage and database will use this id to save to street food.
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
                                                    StreetFood streetFood= new StreetFood(url, enteredName, enteredAddress, enteredArea, enteredCity, enteredPostcode, enteredAbout, vegie);
                                                    dbref.child(id).setValue(streetFood)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    /*Intent will navigate to an other activity.
                                                                     * Declared parameter is the content and the java class of the activity where will be navigate.*/
                                                                    Intent i = new Intent(SfAddNewPlace.this, SfListOfStreetFoods.class);
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
                                                    Toast.makeText(SfAddNewPlace.this, "Internet disruption", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            })
                            // If the whole upload process fails then warning the user of network issues
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SfAddNewPlace.this, "Internet disruption, try again please", Toast.LENGTH_LONG).show();
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
        if (requestCode == 104 && resultCode == RESULT_OK && data.getData() != null) {
        Picasso.get().load(data.getData()).fit().into(sfpic);
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
