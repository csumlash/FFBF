package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

public class EateryTemplate implements Parcelable {
    private String picURL, name, address, area, city, postcode, about;
    private int priceCat;

    public EateryTemplate(String picURL, String name, String type, String address, String area, String city, String postcode, String about, int priceCat) {
        this.picURL = picURL;
        this.name = name;
        this.address = address;
        this.area = area;
        this.city = city;
        this.postcode = postcode;
        this.about = about;
        this.priceCat = priceCat;
    }

    protected EateryTemplate(Parcel in) {
        picURL = in.readString();
        name = in.readString();
        address = in.readString();
        area = in.readString();
        city = in.readString();
        postcode = in.readString();
        about = in.readString();
        priceCat = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picURL);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(area);
        dest.writeString(city);
        dest.writeString(postcode);
        dest.writeString(about);
        dest.writeInt(priceCat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public int getPriceCat() {
        return priceCat;
    }

    public void setPriceCat(int priceCat) {
        this.priceCat = priceCat;
    }
}
