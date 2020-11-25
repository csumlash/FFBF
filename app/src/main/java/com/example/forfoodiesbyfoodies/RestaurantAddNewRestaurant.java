package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RestaurantAddNewRestaurant extends AppCompatActivity {


    ImageView restAnrImage;
    RadioGroup restAnrPrices;
    Button restAnrUpload, restAnrSend;
    EditText restAnrNameOfRestaurant, restAnrAddressLine, restAnrPostcode, restAnrAreaName, restAnrCity, restAnrTypeOfRestaurant, restAnrDescription;
    TextView restAnrWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_new_restaurant);

        restAnrImage = findViewById(R.id.iv_rest_anr_image);
        restAnrPrices = findViewById(R.id.rg_rest_anr_prices);
        restAnrUpload = findViewById(R.id.btn_rest_anr_upload);
        restAnrNameOfRestaurant = findViewById(R.id.et_rest_anr_nameofrestaurant);
        restAnrAddressLine = findViewById(R.id.et_rest_anr_addressline);
        restAnrPostcode = findViewById(R.id.et_rest_anr_postcode);
        restAnrAreaName = findViewById(R.id.et_rest_anr_areaname);
        restAnrCity = findViewById(R.id.et_rest_anr_city);
        restAnrTypeOfRestaurant = findViewById(R.id.et_rest_anr_typeofrest);
        restAnrDescription = findViewById(R.id.et_rest_anr_desciption);
        restAnrWarning = findViewById(R.id.tv_rest_anr_warning);
        restAnrSend = findViewById(R.id.btn_rest_anr_send);
    }
}