package com.example.assignment1.messages;

import org.json.JSONException;
import org.json.JSONObject;

public class MembersInAGroupRequest {

    private String group;

    public MembersInAGroupRequest(String group) {

        this.group = group;
    }

    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("type", "members");

            jsonObject.put("group", group);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
