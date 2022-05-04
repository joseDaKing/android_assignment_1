package com.example.assignment1.messages;
import org.json.JSONException;
import org.json.JSONObject;


public class CurrentGroupsRequest {

    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("type", "groups");
        }
        catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
