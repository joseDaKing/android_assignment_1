package com.example.assignment1;

import com.google.android.gms.maps.model.LatLng;

public class UserPosition {

    private String name;

    private LatLng latLng;

    public UserPosition(String name, LatLng latLng) {

        this.name = name;

        this.latLng = latLng;
    }

    public String getName() {

        return name;
    }

    public LatLng getLatLng() {

        return latLng;
    }
}
