package com.ratings.doctors.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START doctor_class]
@IgnoreExtraProperties
public class Doctor {
    public String clinicName;
    public String specialist;
    public double phone;
    public double lng;
    public double lat;
    public String area;
    public String url;
    public String address;

    public Doctor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Doctor(String clinicName, String specialist, double phone, double lng, double lat, String area, String url, String address) {
        this.clinicName = clinicName;
        this.specialist = specialist;
        this.phone = phone;
        this.lng = lng;
        this.lat = lat;
        this.area = area;
        this.url = url;
        this.address = address;
    }
}
