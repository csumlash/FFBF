package com.example.forfoodiesbyfoodies;

import android.os.Parcel;

public class StreetFood extends EateryTemplate {

    private boolean isVegan;

    public StreetFood(String picURL, String name, String type, String address, String area, String city, String postcode, String about, int priceCat, boolean isVegan) {
        super(picURL, name, type, address, area, city, postcode, about, priceCat);
        this.isVegan = isVegan;
    }

    protected StreetFood(Parcel in) {
        super(in);
        /* TALÁLJ ERRE KI VALAMI */
        /*if (isVegan == true){
            isVegan = in.readString();
        }*/

    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }
}
