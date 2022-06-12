package com.example.assignment1.client;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTCP {

    private static final String TAG = "AppClientTCP";

    private Socket socket;

    private InputStream inputStream;

    private DataInputStream dataInputStream;

    private OutputStream outputStream;

    private DataOutputStream dataOutputStream;



    public void write(String input) throws IOException {

        if (dataInputStream != null) {

            dataOutputStream.writeUTF(input);

            dataOutputStream.flush();
        }

        Log.d(TAG, "write: is dataInputStream " + (dataInputStream == null));
    }

    public String read() throws IOException {

        String text = dataInputStream.readUTF();

        Log.d(TAG, "read: " + text);

        return text;
    }



    public void connect() throws IOException {

        socket = new Socket("195.178.227.53", 7117);

        inputStream = socket.getInputStream();

        dataInputStream = new DataInputStream(inputStream);

        outputStream = socket.getOutputStream();

        dataOutputStream = new DataOutputStream(outputStream);

        Log.d(TAG, "connect: ");
    }

    public void disconnect() throws IOException {

        socket.close();

        Log.d(TAG, "disconnect: ");
    }
}
