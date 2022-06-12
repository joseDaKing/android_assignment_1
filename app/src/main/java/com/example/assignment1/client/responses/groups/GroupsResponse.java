package com.example.assignment1.client.responses.groups;

import com.example.assignment1.interfaces.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupsResponse implements Response<GroupsResponseData> {

    private String jsonString;

    public GroupsResponse(String jsonString) {

        this.jsonString = jsonString;
    }

    @Override
    public GroupsResponseData data() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        GroupsResponseData groupsResponseData = new GroupsResponseData();

        groupsResponseData.type = jsonObject.getString("type");

        JSONArray jsonGroup = jsonObject.getJSONArray("groups");

        String[] groups = new String[jsonGroup.length()];

        for (int i = 0; i < jsonGroup.length(); i++) {

            groups[i] = jsonGroup.getJSONObject(i).getString("group");
        }

        groupsResponseData.groups = groups;

        return groupsResponseData;
    }
}
