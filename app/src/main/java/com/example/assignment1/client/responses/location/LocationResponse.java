package com.example.assignment1.client.responses.location;

import com.example.assignment1.interfaces.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationResponse implements Response<LocationResponseData> {

    private String jsonString;

    public LocationResponse(String jsonString) {

        this.jsonString = jsonString;
    }

    @Override
    public LocationResponseData data() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        LocationResponseData locationResponseData = new LocationResponseData();

        locationResponseData.type = jsonObject.getString("type");

        locationResponseData.id = jsonObject.getString("id");

        locationResponseData.latitude = jsonObject.getString("latitude");

        locationResponseData.longitude = jsonObject.getString("longitude");

        return locationResponseData;
    }
}
