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
import android.widget.RadioGroup;
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

    
    ImageView image;
    Button send;
    EditText name, address, postcode, area, city, type, about;
    TextView warning;
    DatabaseReference dbref;
    StorageReference sref;
    Uri image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_new_restaurant);

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
        sref = FirebaseStorage.getInstance().getReference("images");
        dbref = FirebaseDatabase.getInstance().getReference("restaurants");

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String enteredName = name.getText().toString();
                String enteredAddress = address.getText().toString();
                String enteredArea = area.getText().toString();
                String enteredCity = city.getText().toString();
                String enteredPostcode = postcode.getText().toString();
                String enteredAbout = about.getText().toString();
                String enteredType = type.getText().toString();
                String id = dbref.push().getKey();

                if (enteredName.length() > 0 && enteredAddress.length() > 0 && id.length() > 0) {
                    Restaurant rest = new Restaurant("", enteredName, enteredAddress, enteredArea, enteredCity, enteredPostcode, enteredAbout, enteredType);
                    dbref.child(dbref.push().getKey()).setValue(rest)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent i = new Intent(RestaurantAddNewRestaurant.this, Dashboard.class);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RestaurantAddNewRestaurant.this, "Please provide name of restaurant and address.", Toast.LENGTH_LONG).show();
                        }
                    });


                    //Images will get id after uploaded to images cloud storage and database will use this id to save to restaurants.
                    final StorageReference reference = sref.child(id + "." + getExtension(image_path)); //This method will return the extension of the image to name it.

                    //The next part is the uploadd the image and check that was successfully upload or failed.
                    reference.putFile(image_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //This record is not going to go to the next activity without the URL of the image, only if the method is successful.
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    Intent i = new Intent(RestaurantAddNewRestaurant.this, Dashboard.class);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    reference.delete();
                                    Toast.makeText(RestaurantAddNewRestaurant.this, "Internet disruption", Toast.LENGTH_LONG).show();
                                }
                            });
                            //If fail to get the URL of the image, it necessary to delete that kind of orphan image from the database, because no one can access to it and just it takes place.
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }
        });


        //Image View will respond to click to choose the picture.
        //Intent used like default constructor to enter the phone directory and choose a picture.
        //Declare what kind of files laud to use for the picture.
        //Bring the selected image to the app.
        //Given an ID for my request code to identify when this task will be finished.
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 105);
            }
        });
    }
    //Any time a task will finish the onAvtivityResult will execute and the parameters will find the specified request code.
    //This method will be use if statement to specify if the activity was finish or not.
    //The result code will tell if the task was run successfully.
    //The uploaded image will force to fit in the size of the image view.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 105 && resultCode == RESULT_OK && data.getData() != null){
            Picasso.get().load(data.getData()).fit().into(image);
            image_path = data.getData();
        }
    }

    //This method will get the file extension number and returned to the file type.
    private String getExtension(Uri path){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(path));

    }
}