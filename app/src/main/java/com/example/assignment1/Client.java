package com.example.assignment1;


import com.example.assignment1.messages.CurrentGroupsRequest;
import com.example.assignment1.messages.CurrentGroupsResponse;
import com.example.assignment1.messages.DeregistrationRequest;
import com.example.assignment1.messages.MembersInAGroupRequest;
import com.example.assignment1.messages.MembersInAGroupResponse;
import com.example.assignment1.messages.RegistrationRequest;
import com.example.assignment1.messages.RegistrationResponse;
import com.example.assignment1.messages.SetPositionRequest;

import org.json.JSONException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {

    private final String ipAddress;

    private final String port;

    private ArrayList<IChangeListener<String[]>> onGroups = new ArrayList<>();

    private ArrayList<OnGroupMember> onGroupMembers = new ArrayList<OnGroupMember>();

    private DataInputStream dataInputStream;

    private DataOutputStream dataOutputStream;

    private boolean isTerminating = false;

    public Client(String ipAddress, String port) throws IOException {

        this.ipAddress = ipAddress;

        this.port = port;

        Socket socket = new Socket(ipAddress, Integer.parseInt(port));

        InputStream inputStream = socket.getInputStream();

        dataInputStream = new DataInputStream(inputStream);

        OutputStream outputStream = socket.getOutputStream();

        dataOutputStream = new DataOutputStream(outputStream);
    }

    public String register(RegistrationRequest request) {

        String id = null;

        try {
            dataOutputStream.writeUTF(request.toString());

            RegistrationResponse registrationResponse = new RegistrationResponse(dataInputStream.readUTF());

            id = registrationResponse.value();
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return id;
    }

    public void unregister(DeregistrationRequest request) {

        try {
            dataOutputStream.writeUTF(request.toString());
            dataOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(SetPositionRequest request) {

        try {
            dataOutputStream.writeUTF(request.toString());
            dataOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getAllGroups() throws JSONException {

        CurrentGroupsResponse response = null;

        try {
            dataOutputStream.writeUTF(new CurrentGroupsRequest().toString());
            dataOutputStream.flush();
            response = new CurrentGroupsResponse(dataInputStream.readUTF());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return response.value();
    }

    private String[] getGroupMembers(MembersInAGroupRequest request) throws JSONException {

        MembersInAGroupResponse response = null;

        try {
            dataOutputStream.writeUTF(request.toString());
            dataOutputStream.flush();
            response = new MembersInAGroupResponse(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.value();
    }

    public void onGroups(IChangeListener<String[]> listener) {

        onGroups.add(listener);
    }

    private void executeOnGroups(String[] groupNames) {

        for (int i = 0; i < onGroups.size(); i++) {

            IChangeListener<String[]> listener = onGroups.get(i);

            if (listener != null) {

                listener.changeListener(groupNames);
            }
        }
    }

    public void onGroupMembers(String group, IChangeListener<String[]> listener) {

        onGroupMembers.add(new OnGroupMember(group, listener));
    }

    public void terminate() {

        if (!isTerminating) {

            isTerminating = true;
        }

        onGroups.clear();

        onGroupMembers.clear();
    }

    public void run() {

        while(!isTerminating) {

            try {
                executeOnGroups(getAllGroups());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }



            // Executing the onGroupMembers listeners
            for (int i = 0; i < onGroupMembers.size(); i++) {

                OnGroupMember onGroupMember = onGroupMembers.get(i);

                String name = onGroupMember.getName();

                try {
                    String[] members = getGroupMembers(new MembersInAGroupRequest(name));

                    onGroupMember.getListener().changeListener(members);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            try {
                Thread.sleep(250);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
