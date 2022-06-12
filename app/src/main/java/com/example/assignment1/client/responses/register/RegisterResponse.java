package com.example.assignment1.client.responses.register;

import com.example.assignment1.interfaces.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterResponse implements Response<RegisterResponseData> {

    private String jsonString;

    public RegisterResponse(String jsonString) {

        this.jsonString = jsonString;
    }

    @Override
    public RegisterResponseData data() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        RegisterResponseData registerResponseData = new RegisterResponseData();

        registerResponseData.type = jsonObject.getString("type");

        registerResponseData.id = jsonObject.getString("id");

        registerResponseData.group = jsonObject.getString("group");

        return registerResponseData;
    }
}
