package com.example.forfoodiesbyfoodies;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SfAddNewPlace extends AppCompatActivity {

    Button send;
    ImageView sfpic;
    TextView warning;
    EditText sfname, address, postcode, area, city, about;
    Switch vegie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_add_new_place);

        send = findViewById(R.id.btn_sf_anp_send);
        sfpic = findViewById(R.id.iv_sf_anp_image);
        warning = findViewById(R.id.tv_sf_anp_warning);
        sfname = findViewById(R.id.et_sf_anp_nameofsf);
        address = findViewById(R.id.et_sf_anp_addressline);
        postcode = findViewById(R.id.et_sf_anp_postcode);
        area = findViewById(R.id.et_sf_anp_areaname);
        city = findViewById(R.id.et_sf_anp_city);
        about = findViewById(R.id.et_sf_anp_about);
        vegie = findViewById(R.id.switch_sf_anp_vegie);
        
    }
}