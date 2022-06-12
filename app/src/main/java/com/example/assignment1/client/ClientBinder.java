package com.example.assignment1.client;

import android.os.Binder;

public class ClientBinder extends Binder {

    private ClientService service;

    public ClientBinder(ClientService service) {

        this.service = service;
    }

    public ClientService getService() {

        return service;
    }
}
