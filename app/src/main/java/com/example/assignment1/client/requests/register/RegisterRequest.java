package com.example.assignment1.client.requests.register;

import android.util.JsonWriter;

import com.example.assignment1.interfaces.Request;

import java.io.IOException;
import java.io.StringWriter;

public class RegisterRequest implements Request<RegisterRequestData> {

    private final RegisterRequestData registerRequestData = new RegisterRequestData();

    public RegisterRequest group(String group) {

        registerRequestData.group = group;

        return this;
    }

    public RegisterRequest member(String member) {

        registerRequestData.member = member;

        return this;
    }

    public RegisterRequestData data() {

        if (registerRequestData.group == null) {

            throw new Error("group property must be set");
        }

        if (registerRequestData.member == null) {

            throw new Error("member property must be set");
        }

        return registerRequestData;
    }

    @Override
    public String toJSON() throws IOException {

        RegisterRequestData registerRequestData = data();

        JsonWriter jsonWriter = new JsonWriter(new StringWriter());

        jsonWriter
        .beginObject()
            .name("type")
            .value(registerRequestData.type)
            .name("group")
            .value(registerRequestData.group)
            .name("member")
            .value(registerRequestData.member)
        .endObject();

        return jsonWriter.toString();
    }
}

