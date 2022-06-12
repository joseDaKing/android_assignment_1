package com.example.assignment1.client.requests.members;

import com.example.assignment1.client.ClientTypes;
import com.example.assignment1.interfaces.DataObject;

public class MembersRequestData implements DataObject {

    public String type = ClientTypes.MEMBERS;

    public String group = null;
}
