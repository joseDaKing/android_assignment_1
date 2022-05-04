package com.example.assignment1.messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentGroupsResponse implements IResponse<String[]> {

    private JSONObject jsonObject;

    public CurrentGroupsResponse(String json) throws JSONException {

        jsonObject = new JSONObject(json);
    }

    public String[] value() {

        String[] groupNames = new String[0];

        try {

            JSONArray jsonArray = (JSONArray)jsonObject.get("groups");

            int length = jsonArray.length();

            groupNames = new String[length];

            for (int i = 0; i < length; i++) {

                JSONObject item = (JSONObject)jsonArray.get(i);

                String groupName = (String)item.get("group");

                groupNames[i] = groupName;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return groupNames;
    }
}
