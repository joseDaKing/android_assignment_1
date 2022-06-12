package com.example.assignment1.interfaces;

import org.json.JSONException;

public interface Data<T> {

    T data() throws JSONException;
}
