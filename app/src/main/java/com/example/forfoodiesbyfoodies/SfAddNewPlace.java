package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class SfAddNewPlace extends AppCompatActivity {

    Button uploadpic, send;
    ImageView sfpic;
    TextView warning;
    EditText sfname, addressline, postcode, area, city, about;
    Switch vegie;
    RadioGroup pricerate;

    /* ELŐSZÖR AZ EATRYTEMPLATE ALAPJÁN KÉSZÍTS EBBŐL OBJEKTUMOT (MERT A STREET FOOD CLASS NINCS KÉSZ) */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sf_add_new_place);

        uploadpic = findViewById(R.id.btn_sf_anp_upload);
        send = findViewById(R.id.btn_sf_anp_send);
        sfpic = findViewById(R.id.iv_sf_anp_image);
        warning = findViewById(R.id.tv_sf_anp_warning);
        sfname = findViewById(R.id.et_sf_anp_nameofsf);
        addressline = findViewById(R.id.et_sf_anp_addressline);
        postcode = findViewById(R.id.et_sf_anp_postcode);
        area = findViewById(R.id.et_sf_anp_areaname);
        city = findViewById(R.id.et_sf_anp_city);
        about = findViewById(R.id.et_sf_anp_about);
        vegie = findViewById(R.id.switch_sf_anp_vegie);
        pricerate = findViewById(R.id.rg_sf_anp_prices);


    }
}