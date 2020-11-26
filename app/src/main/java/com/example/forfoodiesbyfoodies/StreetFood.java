package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * This class inherits all the attributes and methods from the parent EateryTemplate class and
 * defines all the other details are its own ones but independent from the parent one. */
public class StreetFood extends EateryTemplate implements Parcelable {

    private int isVegan;

    public StreetFood(String picURL, String name, String type, String address, String area, String city, String postcode, String about, int priceCat, int isVegan) {
        super(picURL, name, type, address, area, city, postcode, about, priceCat);
        this.isVegan = isVegan;
    }

    protected StreetFood(Parcel in) {
        super(in);
        isVegan = in.readInt();

    }

    public int isVegan() {
        return isVegan;
    }

    public void setVegan(boolean isVegan) {
        isVegan = isVegan;
    }
}
