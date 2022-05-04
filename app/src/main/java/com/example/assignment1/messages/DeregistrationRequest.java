package com.example.assignment1.messages;
import org.json.JSONException;
import org.json.JSONObject;

public class DeregistrationRequest {

    private String id = "id";

    public DeregistrationRequest(String id) {

        this.id = id;
    }

    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("type", "unregister");

            jsonObject.put("id", id);
        }
        catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
