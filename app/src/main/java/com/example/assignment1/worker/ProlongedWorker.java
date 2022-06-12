package com.example.assignment1.worker;

import com.example.assignment1.interfaces.Worker;

public class ProlongedWorker implements Worker {

    private Thread thread;

    private Runnable runnable;

    public boolean isTerminated() {

        return thread == null;
    }

    public void setRunnable(Runnable runnable) {

        this.runnable = runnable;
    }

    public void initialize() {

        if (thread == null) {

            thread = new Thread(() -> {

                while (!isTerminated()) {

                    if (runnable != null) {

                       runnable.run();
                    }
                }
            });

            thread.start();
        }
    }

    public void terminate() {

        if (thread != null) {

            thread.interrupt();

            thread = null;
        }
    }
}
