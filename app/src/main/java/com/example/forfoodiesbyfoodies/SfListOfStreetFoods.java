package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class SfListOfStreetFoods extends AppCompatActivity {

    Button addnewplace;
    RecyclerView sflist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_list_of_street_foods);


        addnewplace = findViewById(R.id.btn_rest_lorest_addrestaurant);
        sflist = findViewById(R.id.rv_sf_lop_sflist);
    }
}