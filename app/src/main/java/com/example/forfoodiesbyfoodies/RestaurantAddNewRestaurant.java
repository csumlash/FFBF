package com.example.forfoodiesbyfoodies;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantAddNewRestaurant extends AppCompatActivity {


    ImageView image;
    RadioGroup priceRating;
    Button uploadImg, send;
    EditText name, address, postcode, areaname, city, type, about;
    TextView warningMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_new_restaurant);

        image = findViewById(R.id.iv_rest_anr_image);
        priceRating = findViewById(R.id.rg_rest_anr_prices);
        uploadImg = findViewById(R.id.btn_rest_anr_upload);
        name = findViewById(R.id.et_rest_anr_nameofrestaurant);
        address = findViewById(R.id.et_rest_anr_addressline);
        postcode = findViewById(R.id.et_rest_anr_postcode);
        areaname = findViewById(R.id.et_rest_anr_areaname);
        city = findViewById(R.id.et_rest_anr_city);
        type = findViewById(R.id.et_rest_anr_typeofrest);
        about = findViewById(R.id.et_rest_anr_desciption);
        warningMsg = findViewById(R.id.tv_rest_anr_warning);
        send = findViewById(R.id.btn_rest_anr_send);

    }
}