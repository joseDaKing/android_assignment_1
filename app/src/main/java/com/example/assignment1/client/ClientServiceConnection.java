package com.example.assignment1.client;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ClientServiceConnection implements ServiceConnection {

    private ClientBinder clientBinder = null;

    public ClientService getClientService() {

        if (clientBinder != null) {

            return clientBinder.getService();
        }

        return null;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        this.clientBinder = (ClientBinder)iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

        this.clientBinder = null;
    }
}
