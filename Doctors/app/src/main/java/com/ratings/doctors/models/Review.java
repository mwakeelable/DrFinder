package com.ratings.doctors.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START review_class]
@IgnoreExtraProperties
public class Review {
    public String uid;
    public String doctorID;
    public String author;
    public String text;
    public double rate;

    public Review() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    public Review(String uid, String doctorID, String author, String text, double rate) {
        this.uid = uid;
        this.doctorID = doctorID;
        this.author = author;
        this.text = text;
        this.rate = rate;
    }
}
// [END comment_class]
