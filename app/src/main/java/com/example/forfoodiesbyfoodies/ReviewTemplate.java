package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewTemplate implements Parcelable {
    String writer, dateOfVisit, review, reviewKey;
    float rating;

    public ReviewTemplate(String writer, String dateOfVisit, String review, float rating) {
        this.writer = writer;
        this.dateOfVisit = dateOfVisit;
        this.review = review;
        this.rating = rating;
    }


    protected ReviewTemplate(Parcel in) {
        writer = in.readString();
        dateOfVisit = in.readString();
        review = in.readString();
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(writer);
        dest.writeString(dateOfVisit);
        dest.writeString(review);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewTemplate> CREATOR = new Creator<ReviewTemplate>() {
        @Override
        public ReviewTemplate createFromParcel(Parcel in) {
            return new ReviewTemplate(in);
        }

        @Override
        public ReviewTemplate[] newArray(int size) {
            return new ReviewTemplate[size];
        }
    };

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewKey() {
        return reviewKey;
    }

    public void setReviewKey(String reviewKey) {
        this.reviewKey = reviewKey;
    }
}
