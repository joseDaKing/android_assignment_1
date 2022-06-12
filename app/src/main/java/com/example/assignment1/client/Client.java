package com.example.assignment1.client;

import android.util.Log;

import com.example.assignment1.client.responses.ResponseHandler;
import com.example.assignment1.worker.ProlongedWorker;
import com.example.assignment1.worker.TaskWorker;
import com.example.assignment1.interfaces.Request;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private static final String TAG = "AppClient";

    private final ClientTCP clientTCP = new ClientTCP();

    private final TaskWorker taskWorker = new TaskWorker();

    private final ProlongedWorker prolongedWorker = new ProlongedWorker();

    final public ResponseHandler responseHandler = new ResponseHandler();

    public void execute(Request request) {

        taskWorker.execute(() -> {

            try {

                clientTCP.write(request.toJSON());

                Log.e(TAG, "execute: writing to clientTCP");

            } catch (IOException e) {

                Log.d(TAG, "execute: writing to clientTCP failed, message: " + e.getMessage());
            }
        });
    }

    public void connect() {

        taskWorker.initialize();

        final AtomicBoolean isConnected = new AtomicBoolean(false);

        taskWorker.execute(() -> {

            try {

                clientTCP.connect();

                if (!isConnected.get()) {

                    isConnected.set(true);
                }

                Log.d(TAG, "connect: connecting to clientTCP");

            }
            catch (IOException e) {

                Log.e(TAG, "connect: connecting to clientTCP failed, message: " + e.getMessage());
            }
        });

        prolongedWorker.setRunnable(() -> {

            try {

                if (!isConnected.get()) {

                    return;
                }

                String response = clientTCP.read();

                responseHandler.handle(response);

                Log.d(TAG, "connect: reading from clientTCP, message: " + response);
            }
            catch (IOException e) {

                Log.e(TAG, "connect: reading from clientTCP failed, message: " + e.getMessage());
            }
        });

        prolongedWorker.initialize();

        Log.d(TAG, "connect: ");
    }

    public void disconnect() {

        taskWorker.execute(() -> {

            try {
                clientTCP.disconnect();

                Log.d(TAG, "disconnect: disconnecting from clientTCP");
            }
            catch (IOException e) {

                Log.e(TAG, "disconnect: disconnecting from clientTCP failed, message: " + e.getMessage());
            }

            taskWorker.terminate();

            prolongedWorker.terminate();
        });

        taskWorker.terminate();

        prolongedWorker.terminate();

        Log.d(TAG, "disconnect: ");
    }
}