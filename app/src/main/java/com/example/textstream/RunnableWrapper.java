package com.example.textstream;

import java.io.Serializable;

public class RunnableWrapper implements Serializable {
    private transient Runnable runnable;

    public RunnableWrapper(Runnable runnable) {
        this.runnable = runnable;
    }

    public Runnable getRunnable() {
        return runnable;
    }
}
