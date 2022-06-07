package com.example.assignment1;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

interface Callback<T> {
    void call(T value);
}

public class Client implements Runnable {

    private static final String REGISTER_TYPE = "register";

    private static final String MEMBERS_TYPE = "members";

    private static final String GROUPS_TYPE = "groups";

    private static final String LOCATION_TYPE = "location";

    private static final String LOCATIONS_TYPE = "locations";

    private static final String EXCEPTIONS = "exception";



    public static JSONObject createRegistrationRequest(String group, String member) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", REGISTER_TYPE);

        jsonObject.put("group", group);

        jsonObject.put("member", member);

        return jsonObject;
    }

    public static JSONObject createMembersInAGroupRequest(String group) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", MEMBERS_TYPE);

        jsonObject.put("group", group);

        return jsonObject;
    }

    public static JSONObject createGetCurrentGroupsRequest() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", GROUPS_TYPE);

        return jsonObject;
    }

    public static JSONObject createSetPositionRequest(String id, String lon, String lat) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", LOCATION_TYPE);

        jsonObject.put("id", id);

        jsonObject.put("longitude", lon);

        jsonObject.put("latitude", lat);

        return jsonObject;
    }



    public Callback<String> onException;

    public Callback<String[]> onMembers;

    public Callback<String[]> onGroups;

    public Callback<UserPosition[]> onLocations;



    private void handleRequest(String type, JSONObject request) throws JSONException {

        switch (type) {
            case Client.EXCEPTIONS:

                handleException(request, this.onException);

                break;

            case Client.MEMBERS_TYPE:

                handleMembersResponse(request, this.onMembers);

                break;

            case Client.GROUPS_TYPE:

                handleGroupsResponse(request, this.onGroups);

                break;

            case Client.LOCATIONS_TYPE:

                handleLocationsResponse(request, this.onLocations);

                break;
        }
    }

    private static void handleException(JSONObject request, Callback<String> cb) throws JSONException {

        String message = request.getString("message");

        if (cb != null) {

            cb.call(message);
        }
    }

    private static void handleMembersResponse(JSONObject request, Callback<String[]> cb) throws JSONException {

        JSONArray jsonArray = request.getJSONArray("members");

        String[] members = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            members[i] = jsonObject.getString("member");
        }

        if (cb != null) {

            cb.call(members);
        }
    }

    private static void handleGroupsResponse(JSONObject request, Callback<String[]> cb) throws JSONException {

        JSONArray groupsJSON = request.getJSONArray("members");

        String[] groupNames = new String[groupsJSON.length()];

        for (int i = 0; i < groupsJSON.length(); i++) {

            JSONObject jsonObject = groupsJSON.getJSONObject(i);

            groupNames[i] = jsonObject.getString("group");
        }

        if (cb != null) {

            cb.call(groupNames);
        }
    }

    private static void handleLocationsResponse(JSONObject request, Callback<UserPosition[]> cb) throws JSONException {

        JSONArray location = request.getJSONArray("location");

        UserPosition[] users = new UserPosition[location.length()];

        for (int i = 0; i < location.length(); i++) {

            JSONObject user = location.getJSONObject(i);

            String member = user.getString("member");

            String longitude = user.getString("longitude");

            String latitude = user.getString("latitude");

            LatLng latLng = new LatLng(Integer.parseInt(longitude), Integer.parseInt(latitude));

            users[i] = new UserPosition(member, latLng);
        }

        if (cb != null) {

            cb.call(users);
        }
    }



    private String  IP_ADDRESS = "195.178.227.53";

    private int PORT_NUMBER = 7117;

    private Socket socket;

    private InputStream inputStream;

    private DataInputStream dataInputStream;

    private OutputStream outputStream;

    private DataOutputStream dataOutputStream;



    private boolean isTerminating = false;

    private Queue<JSONObject> requestQue = new LinkedList<>();

    public Client() throws IOException {

        socket = new Socket(IP_ADDRESS, PORT_NUMBER);

        inputStream = socket.getInputStream();

        dataInputStream = new DataInputStream(inputStream);

        outputStream = socket.getOutputStream();

        dataOutputStream = new DataOutputStream(outputStream);
    }

    public void addRequest(JSONObject request) {

        requestQue.add(request);
    }

    public void terminate() {

        isTerminating = true;
    }

    @Override
    public void run() {

        while(true) {

            if (requestQue.size() != 0) {

                JSONObject request = requestQue.poll();

                try {

                    dataOutputStream.writeUTF(request.toString());

                    dataOutputStream.flush();

                }
                catch (IOException e) {

                    e.printStackTrace();
                }

                try {

                    String jsonString = dataInputStream.readUTF();

                    JSONObject response = new JSONObject(jsonString);

                    String type = response.getString("type");

                    handleRequest(type, response);

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            if (isTerminating) {

                break;
            }
        }
    }
}
