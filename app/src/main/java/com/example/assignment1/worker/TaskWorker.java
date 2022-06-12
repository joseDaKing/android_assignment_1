package com.example.assignment1.worker;

import com.example.assignment1.Buffer;
import com.example.assignment1.interfaces.Worker;

public class TaskWorker implements Worker {

    private Thread thread;

    private final Buffer<Runnable> buffer = new Buffer<>();

    private final Runnable runnable = () -> {

        while (!isTerminated()) {

            try {
                Runnable task = buffer.get();

                if (task == null) {

                    wait();
                }

                task.run();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public boolean isTerminated() {

        return thread == null;
    }

    public void initialize() {

        if (thread == null) {

            thread = new Thread(runnable);

            thread.start();
        }
    }

    public void terminate() {

        if (thread != null) {

            thread.interrupt();

            thread = null;
        }
    }

    public void execute(Runnable task) {

        buffer.add(task);
    }
}
