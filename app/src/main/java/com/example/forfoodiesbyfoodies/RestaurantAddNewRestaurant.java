package com.example.forfoodiesbyfoodies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;


public class RestaurantAddNewRestaurant extends AppCompatActivity {

    
    ImageView image;
    Button upload, send;
    EditText name, address, postcode, area, city, type, about;
    TextView warning;
    DatabaseReference dbref;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_new_restaurant);

        image = findViewById(R.id.iv_rest_anr_image);
        upload = findViewById(R.id.btn_rest_anr_upload);
        name = findViewById(R.id.et_rest_anr_nameofrestaurant);
        address = findViewById(R.id.et_rest_anr_addressline);
        postcode = findViewById(R.id.et_rest_anr_postcode);
        area = findViewById(R.id.et_rest_anr_areaname);
        city = findViewById(R.id.et_rest_anr_city);
        type = findViewById(R.id.et_rest_anr_typeofrest);
        about = findViewById(R.id.et_rest_anr_desciption);
        warning = findViewById(R.id.tv_rest_anr_warning);
        send = findViewById(R.id.btn_rest_anr_send);
        dbref = FirebaseDatabase.getInstance().getReference("restaurants");


        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String enteredName =  name.getText().toString();
                String enteredAddress = address.getText().toString();
                String enteredArea = area.getText().toString();
                String enteredCity = city.getText().toString();
                String enteredPostcode = postcode.getText().toString();
                String enteredAbout = about.getText().toString();
                String enteredType = type.getText().toString();

                if (enteredName.length() > 0 && enteredAddress.length() > 0){
                    Restaurant rest = new Restaurant("", enteredName, enteredAddress, enteredArea, enteredCity, enteredPostcode, enteredAbout, enteredType);
                    dbref.child(dbref.push().getKey()).setValue(rest);
                }

            }
        });
    }
}