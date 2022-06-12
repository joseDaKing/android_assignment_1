package com.example.assignment1.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ClientService extends Service {

    private static final String TAG = "AppClientService";

    private final IBinder clientBinder = new ClientBinder(this);

    final public Client client = new Client();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return clientBinder;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        client.connect();

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        client.disconnect();

        Log.d(TAG, "onDestroy: ");
    }
}
