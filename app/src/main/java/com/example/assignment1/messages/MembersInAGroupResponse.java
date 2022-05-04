package com.example.assignment1.messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MembersInAGroupResponse implements IResponse<String[]>{

    private JSONObject jsonObject;

    public MembersInAGroupResponse(String json) throws JSONException {

        jsonObject = new JSONObject(json);
    }

    public String[] value() {

        String[] members = new String[0];

        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("members");

            int length = jsonArray.length();

            members = new String[length];

            for (int i = 0; i < length; i++) {

                JSONObject item = (JSONObject)jsonArray.get(i);

                String member = (String)item.get("member");

                members[i] = member;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return members;
    }
}
