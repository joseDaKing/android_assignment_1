package com.example.assignment1.client.responses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment1.client.ClientTypes;
import com.example.assignment1.client.responses.exception.ExceptionResponse;
import com.example.assignment1.client.responses.exception.ExceptionResponseData;
import com.example.assignment1.client.responses.groups.GroupsResponse;
import com.example.assignment1.client.responses.groups.GroupsResponseData;
import com.example.assignment1.client.responses.location.LocationResponse;
import com.example.assignment1.client.responses.location.LocationResponseData;
import com.example.assignment1.client.responses.locations.LocationsResponse;
import com.example.assignment1.client.responses.locations.LocationsResponseData;
import com.example.assignment1.client.responses.members.MembersResponse;
import com.example.assignment1.client.responses.members.MembersResponseData;
import com.example.assignment1.client.responses.register.RegisterResponse;
import com.example.assignment1.client.responses.register.RegisterResponseData;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseHandler {

    protected MutableLiveData<ExceptionResponseData> exceptionResponse = new MutableLiveData<>();

    public LiveData<ExceptionResponseData> getExceptionResponse() {

        return exceptionResponse;
    }

    private MutableLiveData<GroupsResponseData> groupsResponse = new MutableLiveData<>();

    public LiveData<GroupsResponseData> getGroupsResponse() {

        return groupsResponse;
    }

    private MutableLiveData<LocationResponseData> locationResponse = new MutableLiveData<>();

    public LiveData<LocationResponseData> getLocationResponse() {

        return locationResponse;
    }

    private MutableLiveData<LocationsResponseData> locationsResponse = new MutableLiveData<>();

    public LiveData<LocationsResponseData> getLocationsResponse() {

        return locationsResponse;
    }

    private MutableLiveData<MembersResponseData> membersResponse = new MutableLiveData<>();

    public LiveData<MembersResponseData> getMembersResponse() {

        return membersResponse;
    }

    private MutableLiveData<RegisterResponseData> registerResponse = new MutableLiveData<>();

    public LiveData<RegisterResponseData> getRegisterResponse() {

        return registerResponse;
    }

    public void handle(String jsonString) {

        try {

            JSONObject jsonObject = new JSONObject(jsonString);

            String type = jsonObject.getString("type");

            switch (type) {

                case ClientTypes.EXCEPTION:

                    ExceptionResponseData exceptionResponseData = new ExceptionResponse(jsonString).data();

                    exceptionResponse.postValue(exceptionResponseData);
                break;

                case ClientTypes.GROUPS:

                    GroupsResponseData groupsResponseData = new GroupsResponse(jsonString).data();

                    groupsResponse.postValue(groupsResponseData);
                break;

                case ClientTypes.LOCATION:
                    LocationResponseData locationResponseData = new LocationResponse(jsonString).data();

                    locationResponse.postValue(locationResponseData);
                break;

                case ClientTypes.LOCATIONS:
                    LocationsResponseData locationsResponseData = new LocationsResponse(jsonString).data();

                    locationsResponse.postValue(locationsResponseData);
                break;

                case ClientTypes.MEMBERS:
                    MembersResponseData membersResponseData = new MembersResponse(jsonString).data();

                    membersResponse.postValue(membersResponseData);
                break;

                case ClientTypes.REGISTER:
                    RegisterResponseData registerResponseData = new RegisterResponse(jsonString).data();

                    registerResponse.postValue(registerResponseData);
                break;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
