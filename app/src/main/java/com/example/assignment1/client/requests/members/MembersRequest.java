package com.example.assignment1.client.requests.members;

import android.util.JsonWriter;

import com.example.assignment1.interfaces.Request;

import java.io.IOException;
import java.io.StringWriter;

public class MembersRequest implements Request<MembersRequestData> {

    private final MembersRequestData membersRequestData = new MembersRequestData();

    public MembersRequest group(String group) {

        membersRequestData.group = group;

        return this;
    }

    public MembersRequestData data() {

        if (membersRequestData.group == null) {

            throw new Error("group property must be set");
        }

        return membersRequestData;
    }

    @Override
    public String toJSON() throws IOException {

        MembersRequestData membersRequestData = data();

        JsonWriter jsonWriter = new JsonWriter(new StringWriter());

        jsonWriter
        .beginObject()
            .name("type")
            .value(membersRequestData.type)
            .name("group")
            .value(membersRequestData.group)
        .endObject();

        return jsonWriter.toString();
    }
}

