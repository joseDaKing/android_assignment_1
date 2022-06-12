package com.example.assignment1.client.responses.exception;

import com.example.assignment1.interfaces.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionResponse implements Response<ExceptionResponseData> {

    private String jsonString;

    public ExceptionResponse(String jsonString) {

        this.jsonString = jsonString;
    }

    @Override
    public ExceptionResponseData data() throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        ExceptionResponseData exceptionResponseData = new ExceptionResponseData();

        exceptionResponseData.type = jsonObject.getString("type");

        exceptionResponseData.message = jsonObject.getString("id");

        return exceptionResponseData;
    }
}
