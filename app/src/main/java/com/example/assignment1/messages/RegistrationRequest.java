package com.example.assignment1.messages;

import org.json.JSONException;

import org.json.JSONObject;


public class RegistrationRequest {

    private String group;

    private String member;

    public RegistrationRequest(String member, String group) {

        this.member = member;

        this.group = group;
    }

    public String toString() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("type", "register");

            jsonObject.put("member", member);

            jsonObject.put("group", group);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
