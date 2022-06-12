package com.example.assignment1.client.requests.location;

import android.util.JsonWriter;
import com.example.assignment1.interfaces.Request;
import java.io.IOException;
import java.io.StringWriter;

public class LocationRequest implements Request<LocationRequestData> {

    private final LocationRequestData locationRequestData = new LocationRequestData();

    public LocationRequest id(String id) {

        locationRequestData.id = id;

        return this;
    }

    public LocationRequest longitude(String longitude) {

        locationRequestData.longitude = longitude;

        return this;
    }

    public LocationRequest latitude(String latitude) {

        locationRequestData.longitude = latitude;

        return this;
    }

    public LocationRequestData data() {

        if (locationRequestData.longitude == null) {

            throw new Error("longitude property must be set");
        }

        if (locationRequestData.latitude == null) {

            throw new Error("latitude property must be set");
        }

        return locationRequestData;
    }

    @Override
    public String toJSON() throws IOException {

        LocationRequestData locationRequestData = data();


        JsonWriter jsonWriter = new JsonWriter(new StringWriter());

        jsonWriter
        .beginObject()
            .name("type")
            .value(locationRequestData.type)
            .name("id")
            .value(locationRequestData.id)
            .name("longitude")
            .value(locationRequestData.longitude)
            .name("latitude")
            .value(locationRequestData.latitude)
        .endObject();

        return jsonWriter.toString();
    }
}