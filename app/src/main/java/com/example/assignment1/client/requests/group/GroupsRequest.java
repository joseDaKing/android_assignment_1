package com.example.assignment1.client.requests.group;

import android.util.JsonWriter;
import com.example.assignment1.interfaces.Request;
import java.io.IOException;
import java.io.StringWriter;

public class GroupsRequest implements Request<GroupsRequestData> {

    @Override
    public GroupsRequestData data() {
        return new GroupsRequestData();
    }

    @Override
    public String toJSON() throws IOException {
        GroupsRequestData groupsRequestData = data();
        JsonWriter jsonWriter = new JsonWriter(new StringWriter());

        jsonWriter
            .beginObject()
            .name("type")
            .value(groupsRequestData.type)
        .endObject();

        return jsonWriter.toString();
    }
}
