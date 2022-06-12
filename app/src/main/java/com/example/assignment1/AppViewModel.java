package com.example.assignment1;

import androidx.lifecycle.ViewModel;

import com.example.assignment1.client.ClientServiceConnection;

public class AppViewModel extends androidx.lifecycle.ViewModel {

    private ClientServiceConnection clientServiceConnection = new ClientServiceConnection();

    public ClientServiceConnection getClientServiceConnection() {

        return clientServiceConnection;
    }
}
