package com.example.assignment1.client.requests.register;

import com.example.assignment1.client.ClientTypes;
import com.example.assignment1.interfaces.DataObject;

class RegisterRequestData implements DataObject {

    public String type = ClientTypes.REGISTER;

    public String group = null;

    public String member = null;
}