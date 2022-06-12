package com.example.assignment1.client.responses.locations;

import com.example.assignment1.interfaces.DataObject;

public class LocationsResponseData implements DataObject {

    public static class Location {

        public String member = null;

        public String longitude = null;

        public String latitude = null;
    }

    public String type = null;

    public String group = null;

    public Location[] location = null;
}