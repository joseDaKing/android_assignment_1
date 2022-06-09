package com.example.assignment1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ClientService extends Service {

    private Intent intent;

    private Thread thread;

    private LocalBinder localBinder = new LocalBinder();;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return localBinder;
    }

    private class LocalBinder extends Binder {
        public ClientService getService() {

            return ClientService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.intent = intent;

        thread = new Thread(new ClientExecutor(intent));

        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        thread.stop();
    }
}
