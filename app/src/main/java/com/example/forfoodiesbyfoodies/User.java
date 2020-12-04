package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public String username, firstName, lastName, userType, picUrl;

    public User(String username, String firstName, String lastName, String userType, String picUrl) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.picUrl = picUrl;
    }

    public User(Parcel in) {
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        userType = in.readString();
        picUrl = in.readString();
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userType);
        dest.writeString(picUrl);
    }
}
