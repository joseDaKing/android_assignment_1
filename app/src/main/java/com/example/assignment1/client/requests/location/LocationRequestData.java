package com.example.assignment1.client.requests.location;

import com.example.assignment1.client.ClientTypes;
import com.example.assignment1.interfaces.DataObject;

public class LocationRequestData implements DataObject {

    public String type = ClientTypes.LOCATION;

    public String id = null;

    public String longitude = null;

    public String latitude = null;
}