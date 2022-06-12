package com.example.assignment1.client.responses.members;

import com.example.assignment1.interfaces.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MembersResponse implements Response<MembersResponseData> {

    private String jsonString;

    public MembersResponse(String jsonString) {

        this.jsonString = jsonString;
    }

    @Override
    public MembersResponseData data() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        MembersResponseData membersResponseData = new MembersResponseData();

        membersResponseData.type = jsonObject.getString("type");

        membersResponseData.group = jsonObject.getString("group");

        JSONArray jsonMembers = jsonObject.getJSONArray("members");

        String[] members = new String[jsonMembers.length()];

        for (int i = 0; i < jsonMembers.length(); i++) {

            members[i] = jsonMembers.getJSONObject(i).getString("member");
        }

        membersResponseData.members = members;


        return membersResponseData;
    }
}
