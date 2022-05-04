package com.example.assignment1.DataObjects;



public class User {

    private String id;

    private String group;

    private Position position;

    public User(String id, Position position) {

        this.id = id;

        this.position = position;
    }

    public void setGroup(String group) {

        this.group = group;
    }

    public String getGroup() {

        return group;
    }

    public void setPosition(Position position) {

        this.position = position;
    }

    public Position getPosition() {

        return position;
    }
}
