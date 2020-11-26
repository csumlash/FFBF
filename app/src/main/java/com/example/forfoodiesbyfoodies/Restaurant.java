package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

/*
* This class inherits all the attributes and methods from the parent EateryTemplate class and
* defines all the other details are its own ones but independent from the parent one. */
public class Restaurant extends EateryTemplate implements Parcelable {

    // These attributes are uniquely defined in this child Restaurant class beyond the parent ones
    String type;

    /*
    * This constructor executes upon the initialisation of te object and calls the parent (super)
    * constructor to still let it initialises those attributes too with initialisation of the
    * attributes in this child class too. */
    public Restaurant(String picURL, String name, String address, String area, String city, String postcode, String about, int priceCat, String type) {
        super(picURL, name, type, address, area, city, postcode, about, priceCat);
        this.type = type;
    }

    // Similarly, as explained in the parent (super) class, the following methods are for intent-based object transfer works.
    protected Restaurant(Parcel in) {
        super(in);
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    // The following getters and setters are for manipulation and access to the attributes in this child class
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}