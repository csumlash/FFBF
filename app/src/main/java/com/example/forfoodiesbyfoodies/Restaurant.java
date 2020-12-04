package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * This class inherits all the attributes and methods from the parent EateryTemplate class and
 * defines all the other details are its own ones but independent from the parent one. */
public class Restaurant extends EateryTemplate implements Parcelable {

    // The following method is required by the Parcelable implemented class
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
    // These attributes are uniquely defined in this child Restaurant class beyond the parent ones
    String type;
    String link;

    /* This constructor executes upon the initialisation of te object and calls the parent (super)
     * constructor to still let it initialises those attributes too with initialisation of the
     * attributes in this child class too. */
    public Restaurant(String picURL, String name, String address, String area, String city,
                      String postcode, String about, String type, String link) {
        super(picURL, name, address, area, city, postcode, about);
        this.type = type;
        this.link = link;
    }

    // A default constructor that is required by the implemented Parcelable class
    public Restaurant() {
        super();
    }

    // Similarly, as explained in the parent (super) class, the following methods are for intent-based object transfer works.
    protected Restaurant(Parcel in) {
        super(in);
        type = in.readString();
        link = in.readString();
    }

    // This method is required by the Parcelable implemented class to be able to get the object into an Intent
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
        dest.writeString(link);
    }

    // The method below helps the Intent routines to get the object from Intent and then make it usable where needed
    @Override
    public int describeContents() {
        return 0;
    }

    // The following getters and setters are for manipulation and access to the attributes in this child class
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
