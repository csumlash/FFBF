package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String userID, username, firstName, lastName, userType;

    public User(String userID, String username, String firstName, String lastName, String userType) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    protected User(Parcel in) {
        userID = in.readString();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        userType = in.readString();
    }

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userType);
    }
}
