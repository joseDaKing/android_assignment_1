package com.example.assignment1;

import java.io.Serializable;

public interface Callback<T> extends Serializable {

    void call(T value);
}
