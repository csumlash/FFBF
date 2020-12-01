package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * This class inherits all the attributes and methods from the parent EateryTemplate class and
 * defines all the other details are its own ones but independent from the parent one. */
public class StreetFood extends EateryTemplate implements Parcelable {

    String isVegan;

    public StreetFood(String picURL, String name, String address, String area, String city, String postcode, String about, String isVegan) {
        super(picURL, name, address, area, city, postcode, about);
        this.isVegan = isVegan;
    }

    public StreetFood (){
        super();
    }

    protected StreetFood(Parcel in) {
        super(in);
        isVegan = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(isVegan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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


    public String isVegan() {
        return isVegan;
    }

    public void setVegan(boolean isVegan) {
        isVegan = isVegan;
    }
}
