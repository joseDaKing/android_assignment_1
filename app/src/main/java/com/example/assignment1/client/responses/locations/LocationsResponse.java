package com.example.assignment1.client.responses.locations;

import com.example.assignment1.interfaces.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationsResponse implements Response<LocationsResponseData> {

    private String jsonString;

    public LocationsResponse(String jsonString) {

        this.jsonString = jsonString;
    }

    @Override
    public LocationsResponseData data() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        LocationsResponseData locationData = new LocationsResponseData();

        locationData.type = jsonObject.getString("type");

        locationData.group = jsonObject.getString("group");

        JSONArray jsonLocation = jsonObject.getJSONArray("location");

        LocationsResponseData.Location[] location = new LocationsResponseData.Location[jsonLocation.length()];

        for (int i = 0; i < jsonLocation.length(); i++) {

            JSONObject jsonLocationItem = jsonLocation.getJSONObject(i);

            LocationsResponseData.Location locationItem = new LocationsResponseData.Location();

            locationItem.member = jsonLocationItem.getString("member");

            locationItem.latitude = jsonLocationItem.getString("latitude");

            locationItem.longitude = jsonLocationItem.getString("longitude");

            location[i] = locationItem;
        }

        locationData.location = location;

        return locationData;
    }
}
