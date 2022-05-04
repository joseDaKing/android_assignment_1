package com.example.assignment1;

public class OnGroupMember {

    private String id;

    private IChangeListener<String[]> listener;

    public OnGroupMember(String id, IChangeListener<String[]> listener) {

        this.id = id;

        this.listener = listener;
    }

    public IChangeListener<String[]> getListener() {

        return this.listener;
    }

    public String getName() {

        return id;
    }
}
