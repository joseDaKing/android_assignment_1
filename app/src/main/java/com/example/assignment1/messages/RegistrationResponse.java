package com.example.assignment1.messages;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationResponse implements IResponse<String> {

    private JSONObject jsonObject;

    public RegistrationResponse(String json) throws JSONException {

        jsonObject = new JSONObject(json);
    }

    public String value() {

        String value = "";

        try {
            value = (String)jsonObject.get("id");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }
}
