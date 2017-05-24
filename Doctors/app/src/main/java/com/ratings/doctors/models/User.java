package com.ratings.doctors.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public int age;
    public double lng;
    public double lat;
    public String address;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String username, String email, int age, double lng, double lat, String address) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.lng = lng;
        this.lat = lat;
        this.address = address;
    }
}
// [END blog_user_class]
