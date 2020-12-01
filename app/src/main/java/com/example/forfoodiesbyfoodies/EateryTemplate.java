package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

/*
* This class will be the parent class of Restaurant and StreetFood classes
* and contains all the attributes that both child would have too and
* also implements the Parcelable class to let the created objects from this
* class to be transferred through intents from activity to another activity */
public class EateryTemplate implements Parcelable {

    // The following attributes are the basic details of a Street Food place and Restaurant
    private String picURL, name, address, area, city, postcode, about;

    // This constructor sets up all the attributes of the object by got parameters during first initialisation
    public EateryTemplate(String picURL, String name, String address, String area, String city, String postcode, String about) {
        this.picURL = picURL;
        this.name = name;
        this.address = address;
        this.area = area;
        this.city = city;
        this.postcode = postcode;
        this.about = about;

    }

    public EateryTemplate(){

    }

    /*
    * This constructor is responsible to gather the attributes out from the transferred object via
    * intent/putExtra technique and to set up the attributes in the new object by these gathered ones */
    protected EateryTemplate(Parcel in) {
        picURL = in.readString();
        name = in.readString();
        address = in.readString();
        area = in.readString();
        city = in.readString();
        postcode = in.readString();
        about = in.readString();
    }

    /*
    * This method puts the attributes into the intent and lets them
    * to be gathered by another activity when it received them. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picURL);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(area);
        dest.writeString(city);
        dest.writeString(postcode);
        dest.writeString(about);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
    * The methods below provides a solution to make the transferred object details
    * callable as a regular intent extra data. */
    public static final Creator<EateryTemplate> CREATOR = new Creator<EateryTemplate>() {
        @Override
        public EateryTemplate createFromParcel(Parcel in) {
            return new EateryTemplate(in);
        }

        @Override
        public EateryTemplate[] newArray(int size) {
            return new EateryTemplate[size];
        }
    };

    /*
    * The following getters and setters stand for the opportunity to access or manipulate all the
    * attributes are either private or protected inside the object */
    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
