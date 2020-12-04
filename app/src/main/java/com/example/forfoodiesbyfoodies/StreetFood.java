package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * This class inherits all the attributes and methods from the parent EateryTemplate class and
 * defines all the other details are its own ones but independent from the parent one. */
public class StreetFood extends EateryTemplate implements Parcelable {

    public static final Creator<StreetFood> CREATOR = new Creator<StreetFood>() {
        @Override
        public StreetFood createFromParcel(Parcel in) {
            return new StreetFood(in);
        }

        @Override
        public StreetFood[] newArray(int size) {
            return new StreetFood[size];
        }
    };
    String isVeganFriendly;
    String SfKey;

    // Constructor to initialised the members of either this or super class
    public StreetFood(String picURL, String name, String address, String area, String city,
                      String postcode, String about, String isVeganFriendly) {
        super(picURL, name, address, area, city, postcode, about);
        this.isVeganFriendly = isVeganFriendly;
    }

    public StreetFood() {
        super();
    }


    // making the object to be parcelable via intents
    protected StreetFood(Parcel in) {
        super(in);
        isVeganFriendly = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(isVeganFriendly);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters to make the objects ready to be manipulated, red or used during database tasks
    public String getIsVeganFriendly() {
        return isVeganFriendly;
    }

    public void setIsVeganFriendly(String isVeganFriendly) {
        this.isVeganFriendly = isVeganFriendly;
    }

    public String getSfKey() {
        return SfKey;
    }

    public void setSfKey(String sfKey) {
        SfKey = sfKey;
    }
}
