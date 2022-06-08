package com.example.assignment1;

import android.content.Intent;

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

public class ClientExecutor implements Runnable {

    private Intent intent;

    public ClientExecutor(Intent intent) {

        this.intent = intent;
    }

    private void handleRequest(String type, JSONObject request) throws JSONException {

        switch (type) {
            case ClientManager.EXCEPTIONS_TYPE:

                handleException(request);

                break;

            case ClientManager.MEMBERS_TYPE:

                handleMembersResponse(request);

                break;

            case ClientManager.GROUPS_TYPE:

                handleGroupsResponse(request);

                break;

            case ClientManager.LOCATIONS_TYPE:

                handleLocationsResponse(request);

                break;
        }
    }

    private void handleException(JSONObject request) throws JSONException {

        String message = request.getString("message");

        this.intent.putExtra(ClientManager.EXCEPTIONS_TYPE, message);
    }

    private void handleMembersResponse(JSONObject request) throws JSONException {

        JSONArray jsonArray = request.getJSONArray("members");

        String[] members = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            members[i] = jsonObject.getString("member");
        }

        this.intent.putExtra(ClientManager.MEMBERS_TYPE, members);
    }

    private void handleGroupsResponse(JSONObject request) throws JSONException {

        JSONArray groupsJSON = request.getJSONArray("members");

        String[] groupNames = new String[groupsJSON.length()];

        for (int i = 0; i < groupsJSON.length(); i++) {

            JSONObject jsonObject = groupsJSON.getJSONObject(i);

            groupNames[i] = jsonObject.getString("group");
        }

        this.intent.putExtra(ClientManager.GROUPS_TYPE, groupNames);
    }

    private void handleLocationsResponse(JSONObject request) throws JSONException {

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

        intent.putExtra(ClientManager.LOCATIONS_TYPE, users);
    }

    public void run() {

        String  IP_ADDRESS = "195.178.227.53";

        int PORT_NUMBER = 7117;

        Socket socket = null;

        InputStream inputStream = null;

        DataInputStream dataInputStream = null;

        OutputStream outputStream = null;

        DataOutputStream dataOutputStream = null;

        try {
            socket = new Socket(IP_ADDRESS, PORT_NUMBER);

            inputStream = socket.getInputStream();

            dataInputStream = new DataInputStream(inputStream);

            outputStream = socket.getOutputStream();

            dataOutputStream = new DataOutputStream(outputStream);
        }
        catch (IOException e) {}

        while(true) {

            String request = intent.getStringExtra(ClientManager.RECEIVE_TYPE);

            if (request != null) {

                try {

                    dataOutputStream.writeUTF(request);

                    dataOutputStream.flush();

                }
                catch (IOException e) {

                    e.printStackTrace();
                }
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
    }
}
