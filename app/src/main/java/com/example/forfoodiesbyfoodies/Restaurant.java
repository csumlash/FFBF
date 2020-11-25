package com.example.forfoodiesbyfoodies;

import android.os.Parcel;

public class Restaurant extends EateryTemplate{

    String type;

    public Restaurant(String picURL, String name, String address, String area, String city, String postcode, String about, int priceCat, String type) {
        super(picURL, name, type, address, area, city, postcode, about, priceCat);
        this.type = type;
    }

    protected Restaurant(Parcel in) {
        super(in);
        type = in.readString();
    }
}
