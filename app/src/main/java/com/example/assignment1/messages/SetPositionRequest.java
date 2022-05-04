package com.example.assignment1.messages;

import com.example.assignment1.DataObjects.Position;

import org.json.JSONException;

import org.json.JSONObject;

public class SetPositionRequest {

    private String id;

    private Position position;

    public SetPositionRequest(String id, Position position) {

        this.id = id;

        this.position = position;
    }

    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("type", "location");

            jsonObject.put("id", id);

            jsonObject.put("longitude", position.getLat());

            jsonObject.put("latitude", position.getLon());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
