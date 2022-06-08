package com.example.assignment1;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientManager {

    public static final String REGISTER_TYPE = "register";

    public static final String MEMBERS_TYPE = "members";

    public static final String GROUPS_TYPE = "groups";

    public static final String LOCATION_TYPE = "location";

    public static final String LOCATIONS_TYPE = "locations";

    public static final String EXCEPTIONS_TYPE = "exception";

    public static final String RECEIVE_TYPE = "receive";



    public static String createRegistrationRequest(String group, String member) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", REGISTER_TYPE);

        jsonObject.put("group", group);

        jsonObject.put("member", member);

        return jsonObject.toString();
    }

    public static String createMembersInAGroupRequest(String group) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", MEMBERS_TYPE);

        jsonObject.put("group", group);

        return jsonObject.toString();
    }

    public static String createGetCurrentGroupsRequest() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", GROUPS_TYPE);

        return jsonObject.toString();
    }

    public static String createSetPositionRequest(String id, String lon, String lat) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", LOCATION_TYPE);

        jsonObject.put("id", id);

        jsonObject.put("longitude", lon);

        jsonObject.put("latitude", lat);

        return jsonObject.toString();
    }
}
