package com.ratings.doctors.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public int age;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }
}
// [END blog_user_class]
